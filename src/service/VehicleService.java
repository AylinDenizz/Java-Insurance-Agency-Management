package service;

import model.ColorTypeEnum;
import model.InsuranceCompany;
import model.Proposal;
import model.Vehicle;

import java.math.BigDecimal;
import java.util.Date;

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
}
