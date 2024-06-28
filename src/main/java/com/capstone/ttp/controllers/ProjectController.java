package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.entitiy.User;
import com.capstone.ttp.services.AppliedFundingServiceImpl;
import com.capstone.ttp.services.ProjectServiceImpl;
import com.capstone.ttp.services.UserService;
import com.sun.security.auth.UserPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequestMapping("/auth")
@RestController
public class ProjectController {

    private final ProjectServiceImpl projectService;
    private final UserService userService;
    private final AppliedFundingServiceImpl appliedFundingService;

    public ProjectController(ProjectServiceImpl projectService, UserService userService, AppliedFundingServiceImpl appliedFundingService){
        this.projectService = projectService;
        this.userService = userService;
        this.appliedFundingService = appliedFundingService;
    }

    @GetMapping("/admin/projects")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")

    public ResponseEntity<Project> getProjectById(@PathVariable("id") int id) {
        Optional<Project> projectData = projectService.findById(id);

        if (projectData.isPresent()) {
            return new ResponseEntity<>(projectData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("admin/projects")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
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
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable("id") int id, @RequestBody Project project) {
        Project updatedProject = projectService.updateProject(id, project);
        if (updatedProject != null) {
            return new ResponseEntity<>(updatedProject, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("admin/projects/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> deleteProject(@PathVariable("id") int id) {
        try {
            projectService.deleteProject(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/my/projects")
    public ResponseEntity<List<Project>> getMyProjects(@RequestHeader("Username") String username, @RequestParam(required = false) String title){
//        log.info("info" );
        try {
            Optional<User> user = userService.findByEmail(username);
//            log.info("user"+user.get().getId());
            int userId = user.get().getId();
            List<Project> projects = projectService.getProjectsByUserId(userId);

            if (projects.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(projects, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching projects: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/my/projects/{id}")
    public ResponseEntity<?> getMyProjectById(@PathVariable("id") int id) {

        Optional<Project> projectData = projectService.findById(id);

        if (projectData.isPresent()) {
            List<AppliedFunding> appliedFundingData = appliedFundingService.findByProject(projectData.get());

            Map<String, Object> response = new HashMap<>();
            response.put("project", projectData.get());
            response.put("appliedFunding", appliedFundingData);

            return ResponseEntity.ok().body(response);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("my/projects")
    public ResponseEntity<Project> createMyProject(@RequestHeader("Username") String username, @RequestBody Project project) {

        try {
            Optional<User> user = userService.findByEmail(username);
            int userId = user.get().getId();
//            log.info("User id {}", userId);
            if(userId == 0){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
            project.setUserId(userId);
            project.setStatus("Pending");
            Project _project = projectService.createProject(project);
            return new ResponseEntity<>(_project, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("my/projects/{id}")
    public ResponseEntity<Project> updateMyProject(@PathVariable("id") int id, @RequestBody Project project) {
        Optional<Project> proj = projectService.findById(id);
        String status = proj.get().getStatus();
        if(status == null){
            project.setStatus("Pending");
        }else{
            project.setStatus(status);
        }
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
