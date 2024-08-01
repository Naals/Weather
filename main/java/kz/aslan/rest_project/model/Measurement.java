package kz.aslan.rest_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.hibernate.annotations.Cascade;

@Table(name="Measurement")
@Entity
public class Measurement {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="value")
    @Min(value = -100, message = "Value should be greater than -100")
    private double value;

    @Column(name="raining")
    private boolean raining;
    @ManyToOne()
    @JoinColumn(name = "sensor_id",referencedColumnName = "id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Sensor sensor;

    public Measurement() {
    }

    public Measurement(int id, double value, boolean raining) {
        this.id = id;
        this.value = value;
        this.raining = raining;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double isValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean getRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {

        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
