package kz.aslan.rest_project.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import kz.aslan.rest_project.model.Sensor;

public class MeasurementDTO {

    @Min(value = -100, message = "Value should be greater than -100")
    private double value;

    private boolean raining;

    private SensorDTO sensor;

    public MeasurementDTO(double value, Boolean raining, SensorDTO sensor) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
    }

    public MeasurementDTO() {
    }

    public double isValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Boolean getRaining() {
        return raining;
    }

    public void setRaining(Boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
