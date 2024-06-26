package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppliedFundingRepository extends JpaRepository<AppliedFunding, Integer> {
    Optional<AppliedFunding> findByFundingAndProject(Funding funding, Project project);

    List<AppliedFunding> findByProject(Project project);
}
