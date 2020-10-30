package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotPenerbanganModel;

public interface PilotPenerbanganService {
    PilotPenerbanganModel getPilotPenerbanganById(Long id);

    void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan);
}
