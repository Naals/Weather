package kz.aslan.rest_project.services;

import kz.aslan.rest_project.model.Measurement;
import kz.aslan.rest_project.model.Sensor;
import kz.aslan.rest_project.repositories.MeasurementRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementServise {

    private final MeasurementRepos measurementRepos;

    @Autowired
    public MeasurementServise(MeasurementRepos measurementRepos) {
        this.measurementRepos = measurementRepos;
    }

    @Transactional
    public void save(Measurement measurement){
        measurementRepos.save(measurement);
        measurement.getSensor().getMeasurements().add(measurement);
    }

    public List<Measurement> findAll(){
        return measurementRepos.findAll();
    }

    public List<Measurement> findByRainingDays(){
        return measurementRepos.findByRainingTrue();
    }
}
