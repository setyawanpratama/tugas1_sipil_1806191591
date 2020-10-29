package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PenerbanganModel;

import java.util.List;

public interface PenerbanganService {
    void addPenerbangan(PenerbanganModel penerbangan);

    List<PenerbanganModel> getPenerbanganList();

    PenerbanganModel getPenerbanganById(Long Id);

    PenerbanganModel updatePenerbangan(PenerbanganModel penerbangan);

    void deletePenerbangan(PenerbanganModel penerbangan);

    //menambahkan pilot ke suatu penerbangan
    //void addPilotPenerbangan();
}
