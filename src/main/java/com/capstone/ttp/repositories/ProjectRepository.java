package com.capstone.ttp.repositories;

import com.capstone.ttp.entitiy.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    List<Project> findByTitleContaining(String title);
    Page<Project> findByUserId(int userId, Pageable pageable);

    @Query(value = "SELECT * FROM projects WHERE user_id = :userId ORDER BY created_at DESC LIMIT 6", nativeQuery = true)
    List<Project> findTop6Projects(@Param("userId") int userId);
}
