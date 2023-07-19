package service;

import model.InsuranceCompany;
import model.Policy;
import model.Proposal;
import model.Vehicle;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class ProposalService {

    VehicleService vehicleService = new VehicleService();

    public Proposal createProposal(InsuranceCompany company, Vehicle vehicle, BigDecimal offerPrice,
                                   LocalDate startDate, LocalDate endDate, LocalDate expireDate,
                                   BigDecimal discountPrice) {
        Proposal proposal = new Proposal();
        proposal.setEndDate(endDate);
        proposal.setCompany(company);
        proposal.setExpireDate(expireDate);
        proposal.setStartDate(startDate);
        proposal.setVehicle(vehicle);
        proposal.setDiscountPrice(discountPrice);
        proposal.setOfferPrice(calculateAccordingToAccidentOfferPrice(offerPrice, vehicle));
        return proposal;


    }

    public BigDecimal calculateDiscountedPrice(Proposal proposal) {
        if (proposal.getDiscountPrice() != null) {
            return proposal.getOfferPrice().subtract(proposal.getDiscountPrice());
        } else {
            return proposal.getOfferPrice();
        }
    }

    public BigDecimal calculateAccordingToAccidentOfferPrice(BigDecimal offerPrice, Vehicle vehicle) {
        BigDecimal totalOfferPrice = BigDecimal.ZERO;

        BigDecimal totalDamagePrice = vehicleService.totalOfAccidentDamagePrice(vehicle);

        if (totalDamagePrice.compareTo(BigDecimal.ZERO) == 0) {
            totalOfferPrice = offerPrice;
        } else if (totalDamagePrice.compareTo(BigDecimal.ZERO) > 0 && totalDamagePrice.compareTo(new BigDecimal(4000)) <= 0) {
            totalOfferPrice = offerPrice.add(offerPrice.multiply(new BigDecimal(10)).divide(new BigDecimal(100)));
        } else if (totalDamagePrice.compareTo(new BigDecimal(4000)) > 0 && totalDamagePrice.compareTo(new BigDecimal(8000)) <= 0) {
            totalOfferPrice = offerPrice.add(offerPrice.multiply(new BigDecimal(25)).divide(new BigDecimal(100)));
        } else if (totalDamagePrice.compareTo(new BigDecimal(8000)) > 0 && totalDamagePrice.compareTo(new BigDecimal(16000)) <= 0) {
            totalOfferPrice = offerPrice.add(offerPrice.multiply(new BigDecimal(40)).divide(new BigDecimal(100)));
        } else {
            totalOfferPrice = offerPrice.add(offerPrice.multiply(new BigDecimal(80)).divide(new BigDecimal(100)));
        }
        return totalOfferPrice;
    }


}
