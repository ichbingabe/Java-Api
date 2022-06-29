package com.api.parkingcontrol.projections;

import com.api.parkingcontrol.models.CarModel;

public interface LicensePlateProjection {
    String getParkingSpotNumber();
    String getResponsibleName();
    CarModel getCarModel();
    String getApartment();
    String getBlock();
    String getId();
}
