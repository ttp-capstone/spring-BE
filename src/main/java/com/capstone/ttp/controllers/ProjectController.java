package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.entitiy.User;
import com.capstone.ttp.services.AuthUtils;
import com.capstone.ttp.services.ProjectServiceImpl;
import com.sun.security.auth.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/auth")
@RestController
public class ProjectController {

    private final ProjectServiceImpl projectService;
    private AuthUtils authUtils;
    public ProjectController(ProjectServiceImpl projectService){
        this.projectService = projectService;
    }

    @GetMapping("/admin/projects")
    public ResponseEntity<List<Project>> getAllProjects(@RequestParam(required = false) String title){

        try {
            List<Project> projects = projectService.getAllProjects(title);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable("id") int id) {
        Optional<Project> projectData = projectService.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("admin/projects")
    public ResponseEntity<Project> createProject(@RequestBody Project project) {

        try {
            Project _project = projectService.createProject(project);
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("admin/projects/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable("id") int id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("admin/projects/{id}")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") int id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/my/projects")
    public ResponseEntity<List<Project>> getMyProjects(@RequestParam(required = false) String title){

        try {
            int userId = authUtils.getCurrentUserId();
            if(userId == 0){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            log.info("user id {}", userId);
            List<Project> projects = projectService.getProjectsByUserId(userId);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my/projects/{id}")
    public ResponseEntity<Project> getMyProjectById(@PathVariable("id") int id) {

        Optional<Project> projectData = projectService.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("my/projects")
    public ResponseEntity<Project> createMyProject(@RequestBody Project project) {


        try {
            int userId = authUtils.getCurrentUserId();
            log.info("User id {}", userId);
            if(userId == 0){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            project.setUserId(userId);

            Project _project = projectService.createProject(project);
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("my/projects/{id}")
    public ResponseEntity<Project> updateMyProject(@PathVariable("id") int id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("my/projects/{id}")
    public ResponseEntity<HttpStatus> deleteMyProject(@PathVariable("id") int id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
