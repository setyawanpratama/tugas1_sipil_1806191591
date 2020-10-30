package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.model.PilotPenerbanganModel;
import apap.tugas.sipil.repository.PilotPenerbanganDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PilotPenerbanganServiceImpl implements PilotPenerbanganService{
    @Autowired
    PilotPenerbanganDb pilotPenerbanganDb;

    @Override
    public PilotPenerbanganModel getPilotPenerbanganById(Long id){
        return pilotPenerbanganDb.findById(id).get();
    }

    @Override
    public void addPilotPenerbangan(PilotPenerbanganModel pilotPenerbangan){
        pilotPenerbanganDb.save(pilotPenerbangan);
    }
}
