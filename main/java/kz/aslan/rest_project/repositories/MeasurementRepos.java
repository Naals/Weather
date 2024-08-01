package kz.aslan.rest_project.repositories;

import kz.aslan.rest_project.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeasurementRepos extends JpaRepository<Measurement,Integer> {

    List<Measurement> findByRainingTrue();
}
