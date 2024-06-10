package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository){
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> getAllProjects(String title) {
        if (title == null) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findByTitleContaining(title);
        }
    }

    @Override
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    public Optional<Project> findById(int id) {
        return projectRepository.findById(id);
    }

    @Override
    public Project updateProject(int id, Project updatedProject) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setTitle(updatedProject.getTitle());
                    project.setFundingType(updatedProject.getFundingType());
                    project.setFundingType(updatedProject.getFundingType());
                    project.setCountry(updatedProject.getCountry());
                    project.setDescription(updatedProject.getDescription());
                    project.setOrganizationName(updatedProject.getOrganizationName());
                    project.setOrganizationType(updatedProject.getOrganizationType());
                    project.setStatus(updatedProject.getStatus());
                    return projectRepository.save(project);
                })
                .orElse(null);
    }

    @Override
    public void deleteProject(int id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<Project> getProjectsByUserId(int userId) {

            return projectRepository.findByUserId(userId);

    }
}
