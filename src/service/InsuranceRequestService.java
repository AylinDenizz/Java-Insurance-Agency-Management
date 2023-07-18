package service;

import model.*;

import java.util.ArrayList;

public class InsuranceRequestService {

    public InsuranceRequest createInsuranceRequest(Vehicle vehicle) {
        InsuranceRequest insuranceRequest = new InsuranceRequest();
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

    public void addPolicyListToInsuranceRequest(InsuranceRequest insuranceRequest, Policy policy) {
            insuranceRequest.setPolicy(policy);

    }

}


