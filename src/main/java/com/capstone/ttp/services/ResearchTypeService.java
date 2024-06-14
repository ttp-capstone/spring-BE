package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.ResearchType;

import java.util.List;

public interface ResearchTypeService {
    List<ResearchType> getAllResearchTypes(String programName);
    ResearchType createResearchType(ResearchType researchType);
    ResearchType updateResearchType(int id, ResearchType updatedResearchType);
    void deleteResearchType(int id);
}
