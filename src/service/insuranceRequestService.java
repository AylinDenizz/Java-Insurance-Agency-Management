package service;

import model.*;

import java.util.ArrayList;

public class insuranceRequestService {

    public InsuranceRequest createInsuranceRequest(Vehicle vehicle,Policy policy) {
        InsuranceRequest insuranceRequest = new InsuranceRequest();
        insuranceRequest.setPolicy(policy);
        insuranceRequest.setVehicle(vehicle);
        return insuranceRequest;
    }
    public void addProposalListToInsuranceRequest(InsuranceRequest insuranceRequest, Proposal proposal) {
        if(insuranceRequest.getProposalList() != null) {
            insuranceRequest.getProposalList().add(proposal);

        } else {
            ArrayList<Proposal> ProposalList = new ArrayList<>();
            ProposalList.add(proposal);
            insuranceRequest.setProposalList(ProposalList);
        }
    }

}


