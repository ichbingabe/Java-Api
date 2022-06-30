package com.api.parkingcontrol.repositories;

import com.api.parkingcontrol.models.CarModel;
import com.api.parkingcontrol.models.ParkingSpotModel;
import com.api.parkingcontrol.projections.LicensePlateProjection;
import com.api.parkingcontrol.projections.ResponsibleNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParkingSpotRepository extends JpaRepository<ParkingSpotModel, UUID> {
    boolean existsByCarModel(CarModel carModel);
    boolean existsByParkingSpotNumber(String parkingSpotNumber);
    boolean existsByApartmentAndBlock(String apartment, String block);

    List<LicensePlateProjection> findAllParkingSpotByCarModel(CarModel carModel);
    List<ResponsibleNameProjection> findAllParkingSpotByResponsibleName(String responsibleName);
    Optional<ParkingSpotModel> findCarModelByResponsibleName(String responsibleName);
}
