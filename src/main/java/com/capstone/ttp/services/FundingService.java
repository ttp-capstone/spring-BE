package com.capstone.ttp.services;

import com.capstone.ttp.entitiy.Funding;

import java.util.List;

public interface FundingService {
    List<Funding> getAllFunding(String programName);
    Funding createFunding(Funding funding);
    Funding updateFunding(int id, Funding updatedFunding);
    void deleteFunding(int id);
}
