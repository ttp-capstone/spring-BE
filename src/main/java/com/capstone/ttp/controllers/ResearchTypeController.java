package com.capstone.ttp.controllers;

import com.capstone.ttp.entitiy.ResearchType;
import com.capstone.ttp.services.ResearchTypeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class ResearchTypeController {
    private final ResearchTypeServiceImpl researchTypeService;

    public ResearchTypeController(ResearchTypeServiceImpl researchTypeService){
        this.researchTypeService = researchTypeService;
    }

    @GetMapping("/admin/research_types")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<List<ResearchType>> getAllResearchTypes(@RequestParam(required = false) String title){
        try {
            List<ResearchType> researchTypes = researchTypeService.getAllResearchTypes(title);

            if (researchTypes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(researchTypes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/admin/research_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResearchType> getResearchTypeById(@PathVariable("id") int id) {
        Optional<ResearchType> researchTypeData = researchTypeService.findById(id);

        if (researchTypeData.isPresent()) {
            return new ResponseEntity<>(researchTypeData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("admin/research_types")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResearchType> createResearchType(@RequestBody ResearchType researchType) {

        try {
            ResearchType _researchType = researchTypeService.createResearchType(researchType);
            return new ResponseEntity<>(_researchType, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e.toString());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("admin/research_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<ResearchType> updateResearchType(@PathVariable("id") int id, @RequestBody ResearchType researchType) {
        ResearchType updatedresearchType = researchTypeService.updateResearchType(id, researchType);
        if (updatedresearchType != null) {
            return new ResponseEntity<>(updatedresearchType, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("admin/research_types/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    public ResponseEntity<HttpStatus> deleteResearchType(@PathVariable("id") int id) {
        try {
            researchTypeService.deleteResearchType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
