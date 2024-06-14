package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.ResearchType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchTypeRepository extends JpaRepository<ResearchType, Integer> {
    List<ResearchType> findByTitleContaining(String title);
}
