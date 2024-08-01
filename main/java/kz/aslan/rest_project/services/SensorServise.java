package kz.aslan.rest_project.services;

import kz.aslan.rest_project.model.Sensor;
import kz.aslan.rest_project.repositories.SensorRepos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)

public class SensorServise {

    private final SensorRepos sensorRepos;

    public SensorServise(SensorRepos sensorRepos) {
        this.sensorRepos = sensorRepos;
    }

    @Transactional
    public void save(Sensor sensor){
        sensorRepos.save(sensor);
    }

    public List<Sensor> findAll(){
        return sensorRepos.findAll();
    }

    public Sensor findByName(String name) {
        return sensorRepos.findByName(name);
    }
}
