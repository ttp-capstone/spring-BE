package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.Funding;
import com.capstone.ttp.services.FundingServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class FundingController {

    private final FundingServiceImpl fundingService;

    public FundingController(FundingServiceImpl fundingService){
        this.fundingService = fundingService;
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
}
