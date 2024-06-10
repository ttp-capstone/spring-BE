package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.Project;

import java.util.List;

public interface ProjectService {
    List<Project> getAllProjects(String programName);
    Project createProject(Project project);
    Project updateProject(int id, Project updatedProject);
    void deleteProject(int id);
    List<Project> getProjectsByUserId(int userId);
}
