package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByTitleContaining(String title);
    List<Project> findByUserId(int userId);
}
