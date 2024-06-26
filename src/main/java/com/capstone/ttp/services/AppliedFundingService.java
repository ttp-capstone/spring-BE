package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;

import java.util.List;
import java.util.Optional;

public interface AppliedFundingService {
    public Optional<AppliedFunding> findByFundingAndProject(Funding funding, Project project);
    public AppliedFunding saveAppliedFunding(AppliedFunding appliedFunding);
    public List<AppliedFunding> findByProject(Project project);
}
