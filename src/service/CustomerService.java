package service;

import model.BankAccount;
import model.Customer;
import model.CustomerTypeEnum;

import java.util.ArrayList;

public class CustomerService {
    public Customer createCustomer(String name, CustomerTypeEnum customerTypeEnum){
        Customer customer = new  Customer();
        customer.setName(name);
        customer.setCustomerTypeEnum(customerTypeEnum);

        return customer;
    }

    public void addBankAccountToCustomer(Customer customer, BankAccount bankAccount){
        if(customer.getBankAccountList()!=null) {
            customer.getBankAccountList().add(bankAccount);
        }else {
            ArrayList<BankAccount> bankAccountList = new ArrayList<>();
            bankAccountList.add(bankAccount);
            customer.setBankAccountList(bankAccountList);
        }
    }

    public void addPaymentMovementToCustomer(Customer customer, PaymentMovement paymentMovement){
        if(customer.getPaymentMovementList()!=null) {
            customer.getPaymentMovementList().add(paymentMovement);
        }else {
            ArrayList<PaymentMovement> paymentMovementList = new ArrayList<>();
            paymentMovementList.add(paymentMovement);
            customer.setPaymentMovementList(paymentMovementList);
        }
    }





}
