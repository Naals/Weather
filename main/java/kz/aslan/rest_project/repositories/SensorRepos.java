package kz.aslan.rest_project.repositories;

import kz.aslan.rest_project.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepos extends JpaRepository<Sensor, Integer> {
    Sensor findByName(String name);
}
