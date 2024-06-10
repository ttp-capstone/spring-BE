package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.Funding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FundingRepository extends JpaRepository<Funding, Integer> {
    List<Funding> findByProgramNameContaining(String programName);

}
