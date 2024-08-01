package kz.aslan.rest_project.controllers;

import jakarta.validation.Valid;
import kz.aslan.rest_project.dto.SensorDTO;
import kz.aslan.rest_project.model.Sensor;
import kz.aslan.rest_project.services.SensorServise;
import kz.aslan.rest_project.util.SensorAlreadyTakenException;
import kz.aslan.rest_project.util.SensorErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {

    private final SensorServise sensorServise;

    private final ModelMapper modelMapper;

    public SensorController(SensorServise sensorServise, ModelMapper modelMapper) {
        this.sensorServise = sensorServise;
        this.modelMapper = modelMapper;
    }

    @GetMapping()
    public List<SensorDTO> getSensors(){
        return sensorServise.findAll().stream().map(this::convertToSensorDTO).toList();
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError fieldError:errors){
                errorMessage.append(fieldError.getField()).
                        append(" - ").append(fieldError.getDefaultMessage()).
                        append(";");
            }
            List<Sensor> sensors = sensorServise.findAll();
            for(Sensor sensor:sensors){
                if(sensorDTO.getName().equals(sensor.getName())){
                    throw new SensorAlreadyTakenException("The Name already taken!!");
                }
            }
        }
        sensorServise.save(convertToSensor(sensorDTO));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorAlreadyTakenException e){
        SensorErrorResponse personErrorResponse = new SensorErrorResponse(e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(personErrorResponse, HttpStatus.NOT_FOUND);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO,Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor,SensorDTO.class);
    }
}
