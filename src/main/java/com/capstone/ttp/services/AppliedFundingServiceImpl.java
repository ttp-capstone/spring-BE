package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.repositories.AppliedFundingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class AppliedFundingServiceImpl implements AppliedFundingService{

    @Autowired
    private final AppliedFundingRepository appliedFundingRepository;

    public AppliedFundingServiceImpl(AppliedFundingRepository appliedFundingRepository){
        this.appliedFundingRepository = appliedFundingRepository;
    }

    public Optional<AppliedFunding> findByFundingAndProject(Funding funding, Project project) {
        return appliedFundingRepository.findByFundingAndProject(funding, project);
    }

    public AppliedFunding saveAppliedFunding(AppliedFunding appliedFunding) {
        return appliedFundingRepository.save(appliedFunding);
    }

    public List<AppliedFunding> findByProject(Project project) {
        return appliedFundingRepository.findByProject(project);
    }

}
