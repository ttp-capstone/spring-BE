package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.repositories.AppliedFundingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public List<AppliedFunding> findByProject(Project project) {
        List<AppliedFunding> appliedFundingData = appliedFundingRepository.findByProject(project);

        // If you need to fetch Funding data for each AppliedFunding
        appliedFundingData.forEach(appliedFunding -> {
            // Fetch corresponding Funding for each AppliedFunding (assuming there's a relationship)
            Funding funding = appliedFunding.getFunding();
            // Optionally, you can access and use funding data as needed
        });

        return appliedFundingData;
    }

    @Override
    public List<AppliedFunding> findAll() {
        List<AppliedFunding> appliedFundingData = appliedFundingRepository.findAll();

        // If you need to fetch Funding data for each AppliedFunding
        appliedFundingData.forEach(appliedFunding -> {
            // Fetch corresponding Funding for each AppliedFunding (assuming there's a relationship)
            Funding funding = appliedFunding.getFunding();
            // Optionally, you can access and use funding data as needed
        });

        return appliedFundingData;
    }

    @Override
    public int countByStatus(int userId, String status){
        List<AppliedFunding> appliedFundingData = appliedFundingRepository.findByUserIdAndStatus(userId, status);
        if (appliedFundingData == null) {
            return 0;
        }
        return appliedFundingData.size();
    }
    @Override
    public List<AppliedFunding> getTop6AppliedFunding(int userId) {
        return appliedFundingRepository.findTop6AppliedFunding(userId);
    }

    public int countByUserId(int userId){
        List<AppliedFunding> allProjects = appliedFundingRepository.findAll();
        return allProjects.stream()
                .filter(appliedFunding -> appliedFunding.getUserId() == userId)
                .toList().size();

    }
    @Override
    public Page<AppliedFunding> getAppliedFundingByUserId(int userId, int page, int size) {

        return appliedFundingRepository.findByUserId(userId, PageRequest.of(page, size));

    }
}
