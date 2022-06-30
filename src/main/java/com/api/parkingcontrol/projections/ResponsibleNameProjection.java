package com.api.parkingcontrol.projections;

import com.api.parkingcontrol.models.CarModel;

public interface ResponsibleNameProjection {
    String getParkingSpotNumber();
    String getResponsibleName();
    CarModel getCarModel();
    CarModel setCarModel();
    String getBlock();
    String getId();
}
