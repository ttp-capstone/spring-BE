package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppliedFundingRepository extends JpaRepository<AppliedFunding, Integer> {
    Optional<AppliedFunding> findByFundingAndProject(Funding funding, Project project);

    List<AppliedFunding> findByProject(Project project);

    List<AppliedFunding> findByUserIdAndStatus(int userId, String status);

    @Query(value = "SELECT * FROM applied_funding WHERE user_id = :userId ORDER BY created_at DESC LIMIT 6", nativeQuery = true)
    List<AppliedFunding> findTop6AppliedFunding(int userId);

    Page<AppliedFunding> findByUserId(int userId, Pageable pageable);
}
