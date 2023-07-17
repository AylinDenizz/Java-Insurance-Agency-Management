package service;

import model.Accident;

import java.math.BigDecimal;
import java.util.Date;

public class AccidentalService {
    public Accident createAccident(Date accidentalDate, String description, BigDecimal damagePrice, int failureRate) {
        Accident accident = new Accident();
        accident.setAccidentDate(accidentalDate);
        accident.setDescription(description);
        accident.setDamagePrice(damagePrice);
        accident.setFailureRate(failureRate);
        return accident;
    }
}
