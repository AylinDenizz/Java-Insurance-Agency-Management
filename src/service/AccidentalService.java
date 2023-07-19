package service;

import model.Accident;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class AccidentalService {
    public Accident createAccident(LocalDate accidentalDate, String description, BigDecimal damagePrice, int failureRate) {
        Accident accident = new Accident();
        accident.setAccidentDate(accidentalDate);
        accident.setDescription(description);
        accident.setDamagePrice(damagePrice);
        accident.setFailureRate(failureRate);
        return accident;
    }
}
