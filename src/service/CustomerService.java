package service;

import model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerService {

    ProposalService proposalService = new ProposalService();

    public Customer createCustomer(String name, CustomerTypeEnum customerTypeEnum) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setCustomerTypeEnum(customerTypeEnum);


        return customer;
    }

    public void addBankAccountToCustomer(Customer customer, BankAccount bankAccount) {
        if (customer.getBankAccountList() != null) {
            customer.getBankAccountList().add(bankAccount);
        } else {
            ArrayList<BankAccount> bankAccountList = new ArrayList<>();
            bankAccountList.add(bankAccount);
            customer.setBankAccountList(bankAccountList);
        }
    }

    public void addPaymentMovementToCustomer(Customer customer, PaymentMovement paymentMovement) {
        if (customer.getPaymentMovementList() != null) {
            customer.getPaymentMovementList().add(paymentMovement);
        } else {
            ArrayList<PaymentMovement> paymentMovementList = new ArrayList<>();
            paymentMovementList.add(paymentMovement);
            customer.setPaymentMovementList(paymentMovementList);
        }
    }

    public void addInsuranceRequestToCustomer(Customer customer, InsuranceRequest insuranceRequest) {
        if (customer.getInsuranceRequestList() != null) {
            customer.getInsuranceRequestList().add(insuranceRequest);
        } else {
            ArrayList<InsuranceRequest> insuranceRequestList = new ArrayList<>();
            insuranceRequestList.add(insuranceRequest);
            customer.setInsuranceRequestList(insuranceRequestList);
        }
    }

    public void addVehicleToCustomer(Customer customer, Vehicle vehicle) {
        if (customer.getVehicleList() != null) {
            customer.getVehicleList().add(vehicle);
        } else {
            ArrayList<Vehicle> vehicleList = new ArrayList<>();
            vehicleList.add(vehicle);
            customer.setVehicleList(vehicleList);
        }
    }

    public void acceptProposal(Customer customer, Proposal proposal, InsuranceRequest insuranceRequest) {
        List<InsuranceRequest> insuranceRequestList = customer.getInsuranceRequestList();
        for (InsuranceRequest insuranceRequests : insuranceRequestList) {
            if (insuranceRequests.equals(insuranceRequest)) {
                for (Proposal proposal1 : insuranceRequests.getProposalList()) {
                    if (proposal1.equals(proposal) && checkBankAccount(customer,proposalService.calculateDiscountedPrice(proposal1))) {
                        BankAccount customerBankAccount = returnAppropriateBankAccount(customer,
                                proposalService.calculateDiscountedPrice(proposal));
                        if (customerBankAccount != null) {
                            customerBankAccount.setAmount(customerBankAccount.getAmount().subtract(
                                    proposalService.calculateDiscountedPrice(proposal)));
                            proposal.setApproved(true);
                        } else {
                            proposal.setApproved(false);
                        }
                    }
                }
            }
        }
    }


    public boolean checkBankAccount(Customer customer, BigDecimal amount) {
        boolean returnItem = false;
        List<BankAccount> bankAccountList = customer.getBankAccountList();
        for (BankAccount bankAccount : bankAccountList) {

            if (bankAccount.getAmount().compareTo(amount) >= 0) {
                 returnItem = true;
                break;
            }
            else {
                 returnItem = true;
            }
        }
        return returnItem;
    }

    public BankAccount returnAppropriateBankAccount(Customer customer, BigDecimal amount) {
        BankAccount appropriateBankAccount = new BankAccount();

        List<BankAccount> bankAccountList = customer.getBankAccountList();
        for (BankAccount bankAccount : bankAccountList) {

            if (bankAccount.getAmount().compareTo(amount) >= 0) {
                appropriateBankAccount = bankAccount;
            }
        }
        return appropriateBankAccount;
    }




}
