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


        AgencyService agencyService = new AgencyService();
        InsuranceCompanyService insuranceCompanyService = new InsuranceCompanyService();
        BankAccountService bankAccountService = new BankAccountService();
        VehicleService vehicleService = new VehicleService();
        CustomerService customerService = new CustomerService();
        InsuranceService insuranceService = new InsuranceService();
        PaymentMovementService paymentMovementService = new PaymentMovementService();

        Agency agency = agencyService.createAgency("man");

        BankAccount bankAccount = bankAccountService.createBankAccount("Ziraat", "TR55003333225693441",
                BigDecimal.ZERO);
        BankAccount allianzBankAccount = bankAccountService.createBankAccount("Yapı Kredi","TR55000003714862",
                new BigDecimal(1000000000));

        agencyService.addBankAccountToAgency(agency, bankAccount);
        System.out.println(agency.toString());

        InsuranceCompany insuranceCompany = insuranceCompanyService.createInsuranceCompany("Allianz",
                "Çankaya","34659600459872", "Ankara,Çankaya", new BigDecimal(8));

        Insurance insurance = insuranceService.createInsurance("Compulsory traffic insurance",
                InsuranceTypeEnum.COMPULSORY_TRAFFIC_INSURANCE);

        insuranceCompanyService.addInsuranceToInsuranceCompany( insuranceCompany, insurance);
        insuranceCompanyService.addBankAccountToInsuranceCompany(insuranceCompany, allianzBankAccount);

        Customer customer1=customerService.createCustomer("Sinem", CustomerTypeEnum.INDIVIDUAL);

        BankAccount CustomerBankAccount1=bankAccountService.createBankAccount("İş Bankası", "TR55000003849222",
                new BigDecimal(13000));
        customerService.addBankAccountToCustomer(customer1, CustomerBankAccount1);

        agencyService.addCustomerToAgency(agency, customer1);
        Vehicle vehicle1 = vehicleService.createVehicle("Ford", "Fiesta", "45AHD071",
                "1222*4", 2009, ColorTypeEnum.GRAY) ;
        customerService.addVehicleToCustomer(customer1, vehicle1);

        InsuranceRequestService insuranceRequestService = new InsuranceRequestService();
        InsuranceRequest insuranceRequest = insuranceRequestService.createInsuranceRequest(vehicle1);

        customerService.addInsuranceRequestToCustomer(customer1, insuranceRequest);

        LocalDate startDate =LocalDate.of(2023, Month.AUGUST, 31);

        LocalDate endDate =LocalDate.of(2024,Month.AUGUST, 31);
        LocalDate expireDate = startDate.plusDays(3);

        ProposalService proposalService = new ProposalService();
        Proposal proposal1 = proposalService.createProposal(insuranceCompany,vehicle1, new BigDecimal(1000),
                startDate,endDate,expireDate, new BigDecimal(100));

        insuranceRequestService.addProposalListToInsuranceRequest(insuranceRequest,proposal1);
        customerService.addInsuranceRequestToCustomer(customer1,insuranceRequest);

        BigDecimal discountedPrice = proposalService.calculateDiscountedPrice(proposal1);
        customerService.acceptProposal(customer1, proposal1, insuranceRequest);

        PaymentMovement customer1PaymentMovement = paymentMovementService.createPaymentMovementService(
                CustomerBankAccount1,"Insurance Payment",MovementTypeEnum.OUTCOME,discountedPrice);

        customerService.addPaymentMovementToCustomer(customer1,customer1PaymentMovement);








    }
}