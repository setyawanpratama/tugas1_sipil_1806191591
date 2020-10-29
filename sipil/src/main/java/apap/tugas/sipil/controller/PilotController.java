package apap.tugas.sipil.controller;

import apap.tugas.sipil.model.*;
import apap.tugas.sipil.service.AkademiService;
import apap.tugas.sipil.service.MaskapaiService;
import apap.tugas.sipil.service.PilotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PilotController {

    @Qualifier("pilotServiceImpl")
    @Autowired
    private PilotService pilotService;

    @Autowired
    private AkademiService akademiService;

    @Autowired
    private MaskapaiService maskapaiService;

    @GetMapping("/")
    private String home(){
        return "home";
    }

    @GetMapping("/pilot")
    private String viewAllPilot(Model model){
        List<PilotModel> listPilot = pilotService.getPilotList();
        model.addAttribute("listPilot", listPilot);

        return "viewall-pilot";
    }

    @GetMapping("/pilot/tambah")
    private String addPilotFormPage(Model model){
        model.addAttribute("pilot", new PilotModel());

        return "formadd-pilot";
    }

    // masih ada belum bisa narik data untuk generate nip
    @PostMapping(value = "/pilot/tambah/")
    private String addPilot(
            @ModelAttribute PilotModel pilot, Model model
    ){
        String kode_jeniskelamin = pilot.getJenisKelamin().toString();

        String nama = pilot.getNama();
        String kode_nama = nama.substring(nama.length() - 1).toUpperCase();

        Date tanggallahir = pilot.getTanggalLahir();
        DateFormat formatharibulan = new SimpleDateFormat("ddMM");
        DateFormat formattahun = new SimpleDateFormat("YYYY");
        String kode_haribulan = formatharibulan.format(tanggallahir);
        String tahun = formattahun.format(tanggallahir);
        Integer doubtahun = (int)Math.floor(Integer.parseInt(tahun) / 10);
        String kode_tahun = String.valueOf(doubtahun);
        //bikin if else untuk kode_tahun lenght != 3

        String kode_tempatlahir = pilot.getTempatLahir().substring(0,2).toUpperCase();

        Random rnd = new Random();
        char a = (char) ('a' + rnd.nextInt(26));
        char b = (char) ('a' + rnd.nextInt(26));
        String kode_random = ("" + a + b).toUpperCase();

        String nip = kode_jeniskelamin + kode_tempatlahir + kode_nama + kode_haribulan + kode_tahun + kode_random;

        pilot.setNip(nip);

        model.addAttribute("nip", nip);
        pilotService.addPilot(pilot);

        return "add-pilot";
    }

    @GetMapping(value = "/pilot/{nipPilot}")
    private String viewPilot(
        @PathVariable(value = "nipPilot") String nipPilot, Model model
    ){
        PilotModel pilot = pilotService.getPilotByNip(nipPilot);
        AkademiModel akademi = pilot.getAkademiModel();
        MaskapaiModel maskapai = pilot.getMaskapaiModel();

        model.addAttribute("pilot", pilot);
        model.addAttribute("akademi", akademi.getNama());
        model.addAttribute("maskapai", maskapai.getNama());
        return "view-pilot";
    }

    @GetMapping("/pilot/ubah/{nipPilot}")
    private String updatePilotFormPage(
            @PathVariable(value = "nipPilot") String nipPilot, Model model
    ){
        PilotModel pilot = pilotService.getPilotByNip(nipPilot);

        model.addAttribute("pilot", pilot);
        return "formupdate-pilot";
    }

    @PostMapping(value = "/pilot/ubah")
    private String updatePilot(
            @ModelAttribute PilotModel pilot, Model model
    ){
        PilotModel updatedpilot = pilotService.updatePilot(pilot);

        model.addAttribute("nip", updatedpilot.getNip());
        return "update-pilot";
    }

    ///cari/pilot?kodeMaskapai={kodeMaskapai}&idSekolah={idSekolah}

    @GetMapping("/cari")
    private String cariPilotPage(Model model){
        List<MaskapaiModel> maskapai = maskapaiService.getMaskapaiList();
        List<AkademiModel> akademi = akademiService.getAkademiList();

        model.addAttribute("selectedMaskapai", "0");
        model.addAttribute("selectedAkademi", "0");
        model.addAttribute("maskapai", maskapai);
        model.addAttribute("akademi", akademi);
        return "viewcari-pilot";
    }

    @GetMapping("/cari/pilot")
    private String cariPilot(
            @RequestParam(value = "kodeMaskapai") Long idMaskapai,
            @RequestParam(value = "idSekolah") Long idSekolah,
            Model model
    ){
        List<MaskapaiModel> maskapai = maskapaiService.getMaskapaiList();
        List<AkademiModel> akademi = akademiService.getAkademiList();
        List<PilotModel> allPilot = pilotService.getPilotList();

        List<PilotModel> selectedPilot = new ArrayList<>();

        if(idMaskapai != 0 && idSekolah == 0){
            for(PilotModel pilot : allPilot){
                if(pilot.getMaskapaiModel().getId() == idMaskapai){
                    selectedPilot.add(pilot);
                }
            }
        }
        else if(idMaskapai == 0 && idSekolah != 0){
            for(PilotModel pilot : allPilot){
                if(pilot.getAkademiModel().getId() == idSekolah){
                    selectedPilot.add(pilot);
                }
            }
        }
        else if(idMaskapai != 0 && idSekolah != 0){
            for(PilotModel pilot : allPilot){
                if(pilot.getAkademiModel().getId() == idSekolah &&
                pilot.getMaskapaiModel().getId() == idMaskapai){
                    selectedPilot.add(pilot);
                }
            }
        }
        model.addAttribute("selectedMaskapai", idMaskapai);
        model.addAttribute("selectedAkademi", idSekolah);
        model.addAttribute("selectedPilot", selectedPilot);
        model.addAttribute("maskapai", maskapai);
        model.addAttribute("akademi", akademi);
        return "viewcari-pilot";
    }

    @GetMapping("/cari/pilotpenerbanganterbanyak")
    private String cariTigaPilotPage(Model model){
        List<MaskapaiModel> maskapai = maskapaiService.getMaskapaiList();

        model.addAttribute("maskapai", maskapai);
        return "viewcari-tigapilot";
    }

    @GetMapping("/cari/pilot/penerbangan-terbanyak")
    private String cariTigaPilot(
            @RequestParam(value = "kodeMaskapai") Long idMaskapai, Model model
    ){
            if(idMaskapai != 0) {
                MaskapaiModel maskapai = maskapaiService.getMaskapaiById(idMaskapai);
                List<PilotModel> pilot = maskapai.getListPilotMaskapai();

                Map<PilotModel, Integer> mapper = new HashMap<PilotModel, Integer>();

                for (PilotModel each : pilot) {
                    mapper.put(each, each.getPilotPenerbangan().size());
                }

                List<PilotModel> types = mapper.entrySet().stream()
                        .sorted(Comparator.comparing(Map.Entry::getValue))
                        .map(Map.Entry::getKey)
                        .collect(Collectors.toList());

                Collections.reverse(types);

                List<PilotModel> threeselectedpilot = new ArrayList<>();

                if(types.size() >= 3) {
                    for (int i = 0; i < 3; i++) {
                        threeselectedpilot.add(types.get(i));

                        System.out.println(types.get(i).getNama());
                    }
                }
                else{
                    for(PilotModel i : types) {
                        threeselectedpilot.add(i);
                        System.out.println(i.getNama());
                    }
                }
                model.addAttribute("threeselected", threeselectedpilot);
            }

            List<MaskapaiModel> listMaskapai = maskapaiService.getMaskapaiList();

            model.addAttribute("maskapai", listMaskapai);
            return "viewcari-tigapilot";
    }

}