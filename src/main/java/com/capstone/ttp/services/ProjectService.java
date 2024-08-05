package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.Project;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects(String programName);
    Project createProject(Project project);
    Project updateProject(int id, Project updatedProject);
    void deleteProject(int id);
    Page<Project> getProjectsByUserId(int userId, int page, int size);
    List<Project> findAll();
    List<Project> getTop6Projects(int userId);
    List<Project> countByUserId(int userId);

}
