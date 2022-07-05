package com.api.parkingcontrol.controllers;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;
import com.api.parkingcontrol.services.ParkingSpotService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/parking-spot")
public class ParkingSpotController {
    final ParkingSpotService parkingSpotService;
    final ParkingSpotRepository parkingSpotRepository;

    public ParkingSpotController(ParkingSpotService parkingSpotService, ParkingSpotRepository parkingSpotRepository) {
        this.parkingSpotService = parkingSpotService;
        this.parkingSpotRepository = parkingSpotRepository;
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto){
        if(parkingSpotService.existsByCarModel(parkingSpotDto.getCarModel())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Car already registered!");
        }
        if(parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot is already in use!");
        }
        if(parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Apartment has already parking spot!");
        }
        var parkingSpotModel = new ParkingSpotModel();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getParkingSpotById(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(parkingSpotModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpotById(@PathVariable(value = "id") UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(!parkingSpotModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        var parkingSpotModel = parkingSpotModelOptional.get();
        parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
        parkingSpotModel.setCarModel(parkingSpotDto.getCarModel());
        parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
        parkingSpotModel.setApartment(parkingSpotDto.getApartment());
        parkingSpotModel.setBlock(parkingSpotDto.getBlock());

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpotById(@PathVariable(value = "id") UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if(parkingSpotModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found!");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking spot deleted successfully!");
    }

    @GetMapping("/responsible-name/{responsibleName}/car")
    public ResponseEntity<Object> getCarModelByResponsibleName(@PathVariable(value = "responsibleName") String responsibleName){
        Optional<ParkingSpotModel> responsibleNameOptional = parkingSpotService.findCarModelByResponsibleName(responsibleName);
        if(responsibleNameOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Responsible name not found!");
        }
        var parkingSpotModel = responsibleNameOptional.get();
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModel.getCarModel());
    }

    @PutMapping("/responsible-name/{responsibleName}/car")
    public ResponseEntity<Object> putCarModelByResponsibleName(@PathVariable(value = "responsibleName") String responsibleName, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
        Optional<ParkingSpotModel> responsibleNameOptional = parkingSpotService.findCarModelByResponsibleName(responsibleName);
        var parkingSpotModel = responsibleNameOptional.get();
        BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel);

        return ResponseEntity.status(HttpStatus.OK).body(responsibleNameOptional.get());
    }
}
