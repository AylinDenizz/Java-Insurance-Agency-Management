package service;

import model.Accident;
import model.BankAccount;

import java.math.BigDecimal;
import java.util.Date;

public class BankAccountService {

    public BankAccount createBankAccount(String name, String iban, BigDecimal amount) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setIban(iban);
        bankAccount.setName(name);
        bankAccount.setAmount(amount);

        return bankAccount;
    }
}

