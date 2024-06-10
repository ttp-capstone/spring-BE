package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.repositories.FundingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundingServiceImpl implements FundingService {

    @Autowired
    private final FundingRepository fundingRepository;

    public FundingServiceImpl(FundingRepository fundingRepository){
        this.fundingRepository = fundingRepository;
    }

    @Override
    public List<Funding> getAllFunding(String programName) {
        if (programName == null) {
            return fundingRepository.findAll();
        } else {
            return fundingRepository.findByProgramNameContaining(programName);
        }
    }

    @Override
    public Funding createFunding(Funding funding) {
        return fundingRepository.save(funding);
    }

    public Optional<Funding> findById(int id) {
        return fundingRepository.findById(id);
    }

    @Override
    public Funding updateFunding(int id, Funding updatedFunding) {
        return fundingRepository.findById(id)
                .map(funding -> {
                    funding.setProgramName(updatedFunding.getProgramName());
                    funding.setFundingMinistry(updatedFunding.getFundingMinistry());
                    funding.setFundingType(updatedFunding.getFundingType());
                    funding.setFundingAmount(updatedFunding.getFundingAmount());
                    funding.setOverview(updatedFunding.getOverview());
                    funding.setDetails(updatedFunding.getDetails());
                    funding.setQualifications(updatedFunding.getQualifications());
                    funding.setDeadline(updatedFunding.getDeadline());
                    funding.setStatus(updatedFunding.getStatus());
                    funding.setOrgType(updatedFunding.getOrgType());
                    return fundingRepository.save(funding);
                })
                .orElse(null);
    }

    @Override
    public void deleteFunding(int id) {
        fundingRepository.deleteById(id);
    }
}
