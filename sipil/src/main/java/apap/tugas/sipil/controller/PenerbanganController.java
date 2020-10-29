package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.PenerbanganModel;
import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.service.PenerbanganService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            System.out.println(pilot);
        }
        model.addAttribute("allPilot", pilotService.getPilotList());
        model.addAttribute("listPilot", pilot);
        model.addAttribute("penerbangan", penerbangan);
        model.addAttribute("tanggalPenugasan", tanggalPenugasan);
        return "view-penerbangan";
    }

    @PostMapping("/penerbangan/{idPenerbangan}/pilot/tambah")
    private String addPilotToPenerbangan(
            @ModelAttribute PilotModel pilot, Model model
    ){
        //PenerbanganModel penerbangan = penerbanganService.getPenerbanganById(idPenerbangan);
        PilotPenerbanganModel pilotPenerbangan = new PilotPenerbanganModel();
        pilotPenerbangan.setPilotModel(pilot);
        //pilotPenerbangan.setPenerbanganModel(penerbangan);
        //penerbangan.getPenerbanganPilot().add(pilotPenerbangan);

        System.out.println(pilot.getClass().getSimpleName());
        System.out.println(pilot.getNama());
        System.out.println(pilot.getJenisKelamin());

        model.addAttribute("nama", pilot.getNama());
        //model.addAttribute("id", penerbangan.getKode());
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

        model.addAttribute("id", updatedpenerbangan.getId());
        return "update-penerbangan";
    }

    @PostMapping(value = "/penerbangan/hapus")
    private String deletePenerbangan(
        @ModelAttribute PenerbanganModel penerbangan, Model model
    ){
        penerbanganService.deletePenerbangan(penerbangan);

        model.addAttribute("kode", penerbangan.getKode());
        return "delete-penerbangan";
    }
}
