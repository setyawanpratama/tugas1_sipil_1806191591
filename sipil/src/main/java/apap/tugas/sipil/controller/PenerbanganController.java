package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.service.PenerbanganService;
import apap.tugas.sipil.service.PilotPenerbanganService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PenerbanganController {

    @Qualifier("pilotServiceImpl")
    @Autowired
    PilotService pilotService;

    @Autowired
    PenerbanganService penerbanganService;

    @Autowired
    PilotPenerbanganService pilotPenerbanganService;

    @GetMapping("/penerbangan")
    private String ViewAllPenerbangan(Model model){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();

        model.addAttribute("listPenerbangan", listPenerbangan);
        return "viewall-penerbangan";
    }

    @GetMapping("/penerbangan/tambah")
    private String addPenerbanganFormPage(Model model){
        model.addAttribute("penerbangan", new PenerbanganModel());

        return "formadd-penerbangan";
    }

    @PostMapping("/penerbangan/tambah")
    private String addPenerbangan(
            @ModelAttribute PenerbanganModel penerbangan, Model model
    ){
        penerbanganService.addPenerbangan(penerbangan);

        model.addAttribute("penerbangan", penerbangan);
        return "add-penerbangan";
    }

    @GetMapping(value = "/penerbangan/detail/{id}")
    private String viewPenerbangan(
            @PathVariable(value = "id") Long id, Model model
    ){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        List<PilotPenerbanganModel> pilotPenerbangan= penerbangan.getPenerbanganPilot();
        List<PilotModel> pilot = new ArrayList<>();
        Date tanggalPenugasan = null;

        for(PilotPenerbanganModel i : pilotPenerbangan){
            pilot.add(i.getPilotModel());
            if(penerbangan == i.getPenerbanganModel()){
                tanggalPenugasan = i.getTanggalPenugasan();
            }
        }
        //coba-coba
        model.addAttribute("pilotPenerbangan", new PilotPenerbanganModel());

        model.addAttribute("allPilot", pilotService.getPilotList());
        model.addAttribute("listPilot", pilot);
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("tanggalPenugasan", tanggalPenugasan);
        return "view-penerbangan";
    }

    @PostMapping("/penerbangan/{idPenerbangan}/pilot/tambah")
    private String addPilotToPenerbangan(
            @ModelAttribute PilotPenerbanganModel pilotPenerbangan,
            @PathVariable Long idPenerbangan,
            Model model
    ){
        Long Id = pilotPenerbangan.getPilotModel().getId();

        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);

        Date date = new java.util.Date();
        pilotPenerbangan.setTanggalPenugasan(date);

        pilotPenerbangan.setPenerbanganModel(penerbangan);

        List<PilotModel> listPilot = pilotService.getPilotList();
        PilotModel selectedpilot = new PilotModel();

        for(PilotModel pilot: listPilot){
            if(pilot.getId() == Id){
                selectedpilot = pilot;
            }
        }

        pilotPenerbangan.setPilotModel(selectedpilot);

        pilotPenerbanganService.addPilotPenerbangan(pilotPenerbangan);

        model.addAttribute("kode", pilotPenerbangan.getPenerbanganModel().getKode());
        model.addAttribute("nama", selectedpilot.getNama());
        return "add-pilottopenerbangan";
    }

    @GetMapping("/penerbangan/ubah/{idPenerbangan}")
    private String updatePenerbanganFormPage(
            @PathVariable(value = "idPenerbangan") Long idPenerbangan, Model model
    ){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);

        model.addAttribute("penerbangan", penerbangan);
        return "formupdate-penerbangan";
    }

    @PostMapping(value = "/penerbangan/ubah")
    private String updatePenerbangan(
            @ModelAttribute PenerbanganModel penerbangan, Model model
    ){
        PenerbanganModel updatedpenerbangan = penerbanganService.updatePenerbangan(penerbangan);

        model.addAttribute("id", updatedpenerbangan.getKode());
        return "update-penerbangan";
    }

    @PostMapping(value = "/penerbangan/hapus")
    private String deletePenerbangan(
        @ModelAttribute PenerbanganModel penerbangan, Model model
    ){
        // this is stupid (harusnya ada cara yang lebih efisien)
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        List<Long> penerbanganwithpilot = new ArrayList<>();

        for (PenerbanganModel i : listPenerbangan){
            if(i.getPenerbanganPilot().size() > 0){
                penerbanganwithpilot.add(i.getId());
            }
        }

        String kode = penerbangan.getKode();
        if(penerbanganwithpilot.contains(penerbangan.getId())) {
            model.addAttribute("kode", kode);
            return "deletecancel-penerbangan";
        }
        else{
            model.addAttribute("kode", kode);
            penerbanganService.deletePenerbangan(penerbangan);

            return "delete-penerbangan";
        }
    }

    @GetMapping("/penerbangan/hapus/{id}")
    private String deletePenerbanganUsingId(
            @PathVariable(value="id") Long id, Model model
    ){
        PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(id);
        String kode = penerbangan.getKode();

        if(penerbangan.getPenerbanganPilot().size() > 0){
            model.addAttribute("kode", kode);

            return "deletecancel-penerbangan";
        }
        else {
            penerbanganService.deletePenerbangan(penerbangan);
            model.addAttribute("kode", kode);
            return "delete-penerbangan";
        }
    }

    @GetMapping("/cari/pilot/bulan-ini")
    private String pilotBulanIni(Model model){
        List<PenerbanganModel> listPenerbangan = penerbanganService.getPenerbanganList();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        List<PilotModel> pilotBulanIni = new ArrayList<>();

        Integer month = now.getMonthValue();

        for(PenerbanganModel penerbangan : listPenerbangan){
            if(month == penerbangan.getWaktu().getMonth() + 1){
                for(PilotPenerbanganModel pilotPenerbangan : penerbangan.getPenerbanganPilot()){
                    PilotModel pilot = pilotPenerbangan.getPilotModel();
                    if(!pilotBulanIni.contains(pilot)){
                        pilotBulanIni.add(pilotPenerbangan.getPilotModel());
                    }
                }
            }
        }

        model.addAttribute("listPilot", pilotBulanIni);

        return "viewall-pilotbulanini";
    }
}
