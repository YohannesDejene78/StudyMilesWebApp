package com.zenoAppAPI.ZenoWebApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.IncentivesFront;

@Service
public interface IncentiveService {

    IncentivesEntity createIncentive(IncentivesEntity incentivesEntity);

    boolean isExists(Integer incentiveID);

    IncentivesEntity updateIncentive(IncentivesEntity incentive, Integer incentiveID);

    Optional<IncentivesEntity> findSpecificIncentive(Integer incentiveID);

    List<IncentivesEntity> findAll();
    
}
