package service;

import model.InsuranceCompany;
import model.Policy;
import model.Proposal;
import model.Vehicle;

import java.math.BigDecimal;
import java.util.Date;

public class ProposalService {
    public Proposal createProposal(InsuranceCompany company, Vehicle vehicle, BigDecimal offerPrice,
                                   Date startDate, Date endDate, Date expireDate, boolean isApproved,
                                   BigDecimal discountPrice) {
        Proposal proposal = new Proposal();
        proposal.setEndDate(endDate);
        proposal.setCompany(company);
        proposal.setExpireDate(expireDate);
        proposal.setStartDate(startDate);
        proposal.setVehicle(vehicle);
        proposal.setVehicle(vehicle);
        return proposal;
    }
}
