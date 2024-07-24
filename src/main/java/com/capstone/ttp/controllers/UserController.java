package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.entitiy.User;
import com.capstone.ttp.services.AppliedFundingServiceImpl;
import com.capstone.ttp.services.ProjectServiceImpl;
import com.capstone.ttp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;
    private final ProjectServiceImpl projectService;
    private final AppliedFundingServiceImpl appliedFundingService;

    public UserController(UserService userService, ProjectServiceImpl projectService,
                          AppliedFundingServiceImpl appliedFundingService) {

        this.userService = userService;
        this.projectService = projectService;
        this.appliedFundingService = appliedFundingService;
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<User> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User currentUser = (User) authentication.getPrincipal();

        return ResponseEntity.ok(currentUser);
    }

    @GetMapping("/me/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<User>> allUsers() {
        List <User> users = userService.allUsers();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable int id) {
        Optional<User> user = userService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/customer/dashboard_data")
    public ResponseEntity<?> customerDashboardData(@RequestHeader("Username") String username) {
        try {
            Optional<User> user = userService.findByEmail(username);
            int userId = user.get().getId();
//            log.info("User id {}", userId);
            if(userId == 0){
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            int projectCount = projectService.findAll().size();
            int appliedCount = appliedFundingService.findAll().size();
            int acceptedAppliedCount = appliedFundingService.countByStatus("Accepted");
            int rejectedAppliedCount = appliedFundingService.countByStatus("Rejected");
            Map<String, Object> response = new HashMap<>();
            response.put("total_project", projectCount);
            response.put("total_appliedFunding", appliedCount);
            response.put("total_accepted", acceptedAppliedCount);
            response.put("total_rejected", rejectedAppliedCount);

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}