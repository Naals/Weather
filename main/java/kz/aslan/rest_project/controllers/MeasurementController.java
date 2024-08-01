package kz.aslan.rest_project.controllers;

import jakarta.validation.Valid;
import kz.aslan.rest_project.dto.MeasurementDTO;
import kz.aslan.rest_project.model.Measurement;
import kz.aslan.rest_project.model.Sensor;
import kz.aslan.rest_project.services.MeasurementServise;
import kz.aslan.rest_project.services.SensorServise;
import kz.aslan.rest_project.util.SensorAlreadyTakenException;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/measurement")
public class MeasurementController{

    private final MeasurementServise measurementServise;

    private final SensorServise sensorServise;
    private ModelMapper modelMapper;

    public MeasurementController(MeasurementServise measurementServise, SensorServise sensorServise, ModelMapper modelMapper) {
        this.measurementServise = measurementServise;
        this.sensorServise = sensorServise;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError fieldError: errors){
                errorMessage.append(fieldError.getField())
                        .append(" - ").append(fieldError.getDefaultMessage())
                        .append(";");
            }
            return ResponseEntity.badRequest().body(HttpStatus.BAD_REQUEST);
        }

        Sensor sensor = sensorServise.findByName(measurementDTO.getSensor().getName());
        if (sensor == null) {
            throw new SensorAlreadyTakenException("The name of sensor not found!!");
        }

        Measurement measurement = convertToMeasurement(measurementDTO);
        measurement.setSensor(sensor);
        measurementServise.save(measurement);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public List<MeasurementDTO> getRainyDays(){
        return measurementServise.findByRainingDays().stream().map(this::converToMeasurementDTO).toList();
    }

    @GetMapping()
    public List<MeasurementDTO> getMeasurements(){
        return measurementServise.findAll().stream().map(this::converToMeasurementDTO).toList();
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return modelMapper.map(measurementDTO,Measurement.class);
    }

    private MeasurementDTO converToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDTO.class);
    }
}
