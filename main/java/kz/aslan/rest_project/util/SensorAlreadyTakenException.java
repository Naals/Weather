package kz.aslan.rest_project.util;

public class SensorAlreadyTakenException extends RuntimeException{
    public SensorAlreadyTakenException(String m) {
        super(m);
    }
}
