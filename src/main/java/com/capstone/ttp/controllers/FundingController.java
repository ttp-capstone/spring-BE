package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.AppliedFunding;
import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.entitiy.Project;
import com.capstone.ttp.services.AppliedFundingServiceImpl;
import com.capstone.ttp.services.FundingServiceImpl;
import com.capstone.ttp.services.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@RequestMapping("/auth")
@RestController
public class FundingController {

    private final FundingServiceImpl fundingService;
    private final ProjectServiceImpl projectService;
    private final AppliedFundingServiceImpl appliedFundingService;

    public FundingController(FundingServiceImpl fundingService, ProjectServiceImpl projectService, AppliedFundingServiceImpl appliedFundingService){
        this.fundingService = fundingService;
        this.projectService = projectService;
        this.appliedFundingService = appliedFundingService;
    }

    @GetMapping("/admin/funding")

    public ResponseEntity<List<Funding>> getAllFunding(@RequestParam(required = false) String program_name){
        try {
            List<Funding> funding = fundingService.getAllFunding(program_name);

            if (funding.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(funding, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/funding/{id}")

    public ResponseEntity<Funding> getFundingById(@PathVariable("id") int id) {
        Optional<Funding> fundingData = fundingService.findById(id);

        if (fundingData.isPresent()) {
            return new ResponseEntity<>(fundingData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("admin/funding")

    public ResponseEntity<Funding> createFunding(@RequestBody Funding funding) {
        System.out.println(funding);

        try {
            Funding _funding = fundingService.createFunding(funding);
            return new ResponseEntity<>(_funding, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("admin/funding/{id}")

    public ResponseEntity<Funding> updateFunding(@PathVariable("id") int id, @RequestBody Funding funding) {
        Funding updatedFunding = fundingService.updateFunding(id, funding);
        if (updatedFunding != null) {
            return new ResponseEntity<>(updatedFunding, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("admin/funding/{id}")

    public ResponseEntity<HttpStatus> deleteFunding(@PathVariable("id") int id) {
        try {
            fundingService.deleteFunding(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("funding/matched/{id}")
    public ResponseEntity<List<Funding>> matchedFunding(@PathVariable("id") int project_id, @RequestParam(required = false) String program_name) {
        try {
            List<Funding> matchedFunding = new ArrayList<>();
            List<Funding> fundingList = fundingService.getAllFunding(program_name);
            Optional<Project> project = projectService.findById(project_id);
            String orgType = project.get().getOrganizationType();

            if(orgType != null){

                for (Funding funding : fundingList) {
//                    log.info("funding" + funding.getOrgType());
                    if (funding.getOrgType().toLowerCase().contains(orgType.toLowerCase())) {
                        matchedFunding.add(funding);
                    }
                }
            }
            return new ResponseEntity<>(matchedFunding, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view/funding/{id}/{projectId}")
    public ResponseEntity<?> viewFunding(@PathVariable("id") int id, @PathVariable("projectId") int projectId) {

        Optional<Funding> fundingData = fundingService.findById(id);
        Optional<Project> projectData = projectService.findById(projectId);

        if (fundingData.isPresent() && projectData.isPresent()) {
            Optional<AppliedFunding> appliedFundingData = appliedFundingService.findByFundingAndProject(fundingData.get(), projectData.get());
//            log.info("info"+appliedFundingData);
            Map<String, Object> response = new HashMap<>();
            response.put("funding", fundingData.get());
            response.put("projectId", projectId);
            response.put("appliedFunding", appliedFundingData.orElse(null));

            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("apply/funding/{id}/{projectId}")
    public ResponseEntity<?> applyFunding(@PathVariable("id") int id, @PathVariable("projectId") int projectId) {

        Optional<Funding> fundingData = fundingService.findById(id);
        Optional<Project> projectData = projectService.findById(projectId);
        if (fundingData.isPresent() && projectData.isPresent()) {
            AppliedFunding appliedFunding = new AppliedFunding();
            appliedFunding.setFunding(fundingData.get());
            appliedFunding.setProject(projectData.get());

            Date today = new Date();
            appliedFunding.setApplicationDate(today);
            appliedFunding.setStatus("Pending");
            AppliedFunding appliedFunding1 = appliedFundingService.saveAppliedFunding(appliedFunding);
            log.info("applied "+appliedFunding1.toString());
            return new ResponseEntity<>(appliedFunding1, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
