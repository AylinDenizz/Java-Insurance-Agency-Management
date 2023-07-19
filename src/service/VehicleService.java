package service;

import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VehicleService {
    public Vehicle createVehicle(String brand, String model, String plate,
                                 String chassisNumber, int modelYear, ColorTypeEnum color) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(brand);
        vehicle.setColor(color);
        vehicle.setModelYear(modelYear);
        vehicle.setModel(model);
        vehicle.setColor(color);
        vehicle.setChassisNumber(chassisNumber);
        return vehicle;
    }

    public void addAccidentToVehicle(Vehicle vehicle, Accident accident) {
        if (vehicle.getAccidentList() != null) {
            vehicle.getAccidentList().add(accident);

        } else {
            ArrayList<Accident> accidentList = new ArrayList<>();
            accidentList.add(accident);
            vehicle.setAccidentList(accidentList);
        }
    }

    public BigDecimal totalOfAccidentDamagePrice(Vehicle vehicle) {
        BigDecimal total = BigDecimal.ZERO;
        if (vehicle.getAccidentList() != null) {
            for (Accident accident : vehicle.getAccidentList()) {
                total = total.add(accident.getDamagePrice());
            }

        }
        return total;
    }
}
