package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import java.util.List;
import java.util.Optional;

public interface PilotService {
    void addPilot(PilotModel pilot);

    List<PilotModel> getPilotList();

    PilotModel getPilotByNip(String nip);

    PilotModel updatePilot(PilotModel pilot);

    void deletePilot(PilotModel pilot);

    //mencari pilot berdasarkan maskapai dan akademi
    //PilotModel searchPilotByMaskapaiAkademi(Long Id)
}
