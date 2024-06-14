package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.ResearchType;
import com.capstone.ttp.repositories.ResearchTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResearchTypeServiceImpl implements ResearchTypeService{
    @Autowired
    private final ResearchTypeRepository researchTypeRepository;

    public ResearchTypeServiceImpl(ResearchTypeRepository researchTypeRepository){
        this.researchTypeRepository = researchTypeRepository;
    }

    @Override
    public List<ResearchType> getAllResearchTypes(String title) {
        if (title == null) {
            return researchTypeRepository.findAll();
        } else {
            return researchTypeRepository.findByTitleContaining(title);
        }
    }

    @Override
    public ResearchType createResearchType(ResearchType project) {
        return researchTypeRepository.save(project);
    }

    public Optional<ResearchType> findById(int id) {
        return researchTypeRepository.findById(id);
    }

    @Override
    public ResearchType updateResearchType(int id, ResearchType updatedResearchType) {
        return researchTypeRepository.findById(id)
                .map(researchType -> {
                    researchType.setTitle(updatedResearchType.getTitle());
                    researchType.setDescription(updatedResearchType.getDescription());
                    return researchTypeRepository.save(researchType);
                })
                .orElse(null);
    }

    @Override
    public void deleteResearchType(int id) {
        researchTypeRepository.deleteById(id);
    }

}
