import model.*;
import service.*;

import java.math.BigDecimal;
import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        //importing services
        AgencyService agencyService = new AgencyService();
        InsuranceCompanyService insuranceCompanyService = new InsuranceCompanyService();
        BankAccountService bankAccountService = new BankAccountService();
        VehicleService vehicleService = new VehicleService();
        CustomerService customerService = new CustomerService();
        InsuranceService insuranceService = new InsuranceService();
        PaymentMovementService paymentMovementService = new PaymentMovementService();

        //Bank Accounts are created.
        BankAccount agencyBankAccount = bankAccountService.createBankAccount("Ziraat", "TR55003333225693441",
                BigDecimal.ZERO);
        BankAccount allianzBankAccount = bankAccountService.createBankAccount("Yapı Kredi", "TR55000003714862",
                new BigDecimal(1000000000));
        BankAccount CustomerBankAccount1 = bankAccountService.createBankAccount("İş Bankası", "TR55000003849222",
                new BigDecimal(13000));


        // insuranceses are created.
        Insurance insurance = insuranceService.createInsurance("Compulsory traffic insurance",
                InsuranceTypeEnum.COMPULSORY_TRAFFIC_INSURANCE);

        //insurance componies object and setted its proporties.
        InsuranceCompany insuranceCompany = insuranceCompanyService.createInsuranceCompany("Allianz",
                "Çankaya", "34659600459872", "Ankara,Çankaya", new BigDecimal(8));
        insuranceCompanyService.addInsuranceToInsuranceCompany(insuranceCompany, insurance);
        insuranceCompanyService.addBankAccountToInsuranceCompany(insuranceCompany, allianzBankAccount);

        //vehicle object are created.
        Vehicle vehicle1 = vehicleService.createVehicle("Ford", "Fiesta", "45AHD071",
                "1222*4", 2009, ColorTypeEnum.GRAY);


        //customers object is created and  setted its proporties.
        Customer customer1 = customerService.createCustomer("Sinem", CustomerTypeEnum.INDIVIDUAL);
        customerService.addBankAccountToCustomer(customer1, CustomerBankAccount1);
        customerService.addVehicleToCustomer(customer1, vehicle1);

        //creating agency object is created and setted its proporties.
        Agency agency = agencyService.createAgency("man");
        agencyService.addBankAccountToAgency(agency, agencyBankAccount);
        System.out.println(agency.toString());
        agencyService.addCustomerToAgency(agency, customer1);
        agencyService.addInsuranceCompanyToAgency(agency, insuranceCompany);



        //insurance request object is created and added to customer object.
        InsuranceRequestService insuranceRequestService = new InsuranceRequestService();
        InsuranceRequest insuranceRequest = insuranceRequestService.createInsuranceRequest(vehicle1);
        customerService.addInsuranceRequestToCustomer(customer1, insuranceRequest);

        //Proposal object is created.
        LocalDate startDate = LocalDate.of(2023, Month.AUGUST, 31);
        LocalDate endDate = LocalDate.of(2024, Month.AUGUST, 31);
        LocalDate expireDate = startDate.plusDays(3);

        ProposalService proposalService = new ProposalService();
        Proposal proposal1 = proposalService.createProposal(insuranceCompany, vehicle1, new BigDecimal(1000),
                startDate, endDate, expireDate, new BigDecimal(100));

        //  Proposal added to insurance request and added to customer object.
        insuranceRequestService.addProposalListToInsuranceRequest(insuranceRequest, proposal1);
        customerService.addInsuranceRequestToCustomer(customer1, insuranceRequest);

        // discounted price calculated and proposal accepted.
        BigDecimal discountedPrice = proposalService.calculateDiscountedPrice(proposal1);
        customerService.acceptProposal(customer1, proposal1, insuranceRequest);

        // payment movement from customer to agency performed.
        if (proposal1.getApproved()) {
            PaymentMovement customer1PaymenttoAgencyMovement = paymentMovementService.createPaymentMovementService(
                    CustomerBankAccount1, "Insurance Payment", MovementTypeEnum.OUTCOME, discountedPrice);
            customerService.addPaymentMovementToCustomer(customer1, customer1PaymenttoAgencyMovement);

            agencyBankAccount.setAmount(agencyBankAccount.getAmount().add(discountedPrice));
            PaymentMovement toAgencyPaymentMovement =paymentMovementService.createPaymentMovementService(
                    agencyBankAccount,"Insurance İncome", MovementTypeEnum.INCOME, discountedPrice);
            agencyService.addPaymentMovementToAgency(agency,toAgencyPaymentMovement);


        // agency payment transfer to company movement performed.

        agencyBankAccount.setAmount(agencyBankAccount.getAmount().subtract(
                proposalService.calculateDiscountedPrice(proposal1)));
        PaymentMovement agencyPaymenttoCompanyMovement = paymentMovementService.createPaymentMovementService(
                agencyBankAccount, "Insurance Payment Transfer", MovementTypeEnum.OUTCOME, discountedPrice);
        agencyService.addPaymentMovementToAgency(agency,agencyPaymenttoCompanyMovement);

        BankAccount proposalCompanyBankAccount = proposal1.getCompany().getBankAccountList().get(0);
        proposalCompanyBankAccount.setAmount(proposalCompanyBankAccount.getAmount().add(discountedPrice));
        PaymentMovement companyPayment = paymentMovementService.createPaymentMovementService(proposalCompanyBankAccount,
                "Insurance Income", MovementTypeEnum.INCOME, discountedPrice);
        insuranceCompanyService.addPaymentMovementToInsuranceCompany(insuranceCompany,companyPayment );

        // Commision calculation and transfer to agency movement performed.
        BigDecimal commissionAmount = discountedPrice.multiply(proposal1.getCompany().getCommission()).divide(
                new BigDecimal(100));
        proposalCompanyBankAccount.setAmount(proposalCompanyBankAccount.getAmount().subtract(commissionAmount));
        PaymentMovement commisionTransferFromCompanyToAgency = paymentMovementService.createPaymentMovementService(
                proposalCompanyBankAccount, "commision transfer",MovementTypeEnum.OUTCOME,commissionAmount);
        insuranceCompanyService.addPaymentMovementToInsuranceCompany(insuranceCompany,commisionTransferFromCompanyToAgency);

        // Commision income agency movement performed
        agencyBankAccount.setAmount(agencyBankAccount.getAmount().add(commissionAmount));
        PaymentMovement commisionIncomeCompanyToAgency = paymentMovementService.createPaymentMovementService(
                    agencyBankAccount, "commision Income",MovementTypeEnum.INCOME,commissionAmount);
            agencyService.addPaymentMovementToAgency(agency,commisionIncomeCompanyToAgency);

        }
    }
}