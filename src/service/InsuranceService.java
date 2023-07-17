package service;

import model.Insurance;
import model.InsuranceTypeEnum;

public class InsuranceService {

    public Insurance createInsurance(String name, InsuranceTypeEnum insuranceTypeEnum) {
        Insurance insurance = new Insurance();
        insurance.setInsuranceTypeEnum(insuranceTypeEnum);
        insurance.setName(name);
        return insurance;
    }
}
