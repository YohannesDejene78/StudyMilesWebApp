package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.IncentivesFront;
import com.zenoAppAPI.ZenoWebApp.repositories.IncentiveRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.IncentiveService;
import jakarta.persistence.EntityNotFoundException;

@Service
public class IncentiveServiceImpl implements IncentiveService{

    private IncentiveRepository incentiveRepository;
    private IncentivesEntity incentivesEntity;
    private UserAccountInfoRepository userAccountInfoRepository;

    public IncentiveServiceImpl(IncentiveRepository incentiveRepository, IncentivesEntity incentivesEntity,
    UserAccountInfoRepository userAccountInfoRepository){
        this.incentiveRepository=incentiveRepository;
        this.incentivesEntity=incentivesEntity;
        this.userAccountInfoRepository=userAccountInfoRepository;
    }

    @Override
    public IncentivesEntity createIncentive(IncentivesEntity incentivesEntity) {
            Integer userID= incentivesEntity.getUserID().getUserID();
            UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
            .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userID));
            incentivesEntity.setUserID(user);
        return incentiveRepository.save(incentivesEntity);
    }

    @Override
    public boolean isExists(Integer incentiveID) {
        return incentiveRepository.existsById(incentiveID);
    }

    @Override
    public IncentivesEntity updateIncentive(IncentivesEntity incentive, Integer incentiveID) {
        return incentiveRepository.findById(incentiveID).map(incentivesEntity->{
            incentivesEntity.setWalletAddress(incentive.getWalletAddress());
            incentivesEntity.setEarnedTokens(incentive.getEarnedTokens());
            if(incentive.getUserID()!=null && incentive.getUserID().getUserID()!=null){
                Integer userID= incentive.getUserID().getUserID();
                UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userID));
                incentivesEntity.setUserID(user);
            }
            return incentiveRepository.save(incentivesEntity);
        }).orElseThrow(()-> new RuntimeException("Incentive not  found!"));
    }

    @Override
    public Optional<IncentivesEntity> findSpecificIncentive(Integer incentiveID) {
        return incentiveRepository.findById(incentiveID);
    }

    @Override
    public List<IncentivesEntity> findAll() {
        return StreamSupport.stream(incentiveRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }
    
}
