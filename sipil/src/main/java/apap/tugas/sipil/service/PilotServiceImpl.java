package apap.tugas.sipil.service;

import apap.tugas.sipil.model.PilotModel;
import apap.tugas.sipil.repository.PilotDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PilotServiceImpl implements PilotService{
    @Autowired
    PilotDb pilotDb;

    @Override
    public void addPilot(PilotModel pilot){
        pilotDb.save(pilot);
    }

    @Override
    public List<PilotModel> getPilotList(){
        return pilotDb.findAll();
    }

    @Override
    public PilotModel getPilotByNip(String nip){
        return pilotDb.findByNip(nip).get();
    }

    @Override
    public PilotModel updatePilot(PilotModel pilot) {
        pilotDb.save(pilot);

        return pilot;
    }

    @Override
    public void deletePilot(PilotModel pilot) {
        pilotDb.delete(pilot);
    }
}
