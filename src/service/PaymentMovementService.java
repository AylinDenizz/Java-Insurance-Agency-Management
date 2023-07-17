package service;

import model.BankAccount;
import model.MovementTypeEnum;
import model.PaymentMovement;

import java.math.BigDecimal;

public class PaymentMovementService {

    public PaymentMovement createPaymentMovementService(BankAccount bankAccount, String description,
                                                        MovementTypeEnum movementType,BigDecimal amount) {
        PaymentMovement paymentMovement = new PaymentMovement();
        paymentMovement.setMovementType(movementType);
        paymentMovement.setAmount(amount);
        paymentMovement.setDescription(description);
        paymentMovement.setBankAccount(bankAccount);
        return paymentMovement;
    }
}
