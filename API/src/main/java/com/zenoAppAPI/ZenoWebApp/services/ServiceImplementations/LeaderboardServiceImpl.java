package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.LeaderboardEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.repositories.IncentiveRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.LeaderboardRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.LeaderboardService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LeaderboardServiceImpl implements LeaderboardService{

    private LeaderboardRepository leaderboardRepository;
    private UserAccountInfoRepository userAccountInfoRepository;
    private IncentiveRepository incentiveRepository;
    private LeaderboardEntity leaderboardEntity;


    public LeaderboardServiceImpl(LeaderboardRepository leaderboardRepository, UserAccountInfoRepository userAccountInfoRepository,
    IncentiveRepository incentiveRepository, IncentivesEntity incentivesEntity){
        this.leaderboardRepository=leaderboardRepository;
        this.userAccountInfoRepository=userAccountInfoRepository;
        this.incentiveRepository=incentiveRepository;
        this.leaderboardEntity=leaderboardEntity;
    }

    @Override
    public LeaderboardEntity createLeaderboard(LeaderboardEntity newLeaderboardEntity, Integer leaderboardID) {
        Integer userID= newLeaderboardEntity.getUser().getUserID();
        UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
        .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userID));
        newLeaderboardEntity.setUser(user);
        Integer incentiveID= newLeaderboardEntity.getIncentivesID().getIncentivesID();
        IncentivesEntity incentive= incentiveRepository.findById(incentiveID)
        .orElseThrow(() -> new EntityNotFoundException("Incentive not found with ID " + incentiveID));
        newLeaderboardEntity.setIncentivesID(incentive);
        newLeaderboardEntity.setLeaderboardID(leaderboardID);
        return leaderboardRepository.save(newLeaderboardEntity);
    }

    @Override
    public Optional<LeaderboardEntity> findSpecificLeaderboard(Integer leaderboardID) {
        return leaderboardRepository.findById(leaderboardID);
    }

    @Override
    public boolean isExists(Integer leaderboardID) {
        return leaderboardRepository.existsById(leaderboardID);
    }

    @Override
    public LeaderboardEntity updateLeaderboard(LeaderboardEntity leaderboard, Integer leaderboardID) {
        return leaderboardRepository.findById(leaderboardID).map(leaderboardEntity->{
            leaderboardEntity.setTotalXP(leaderboard.getTotalXP());
            if(leaderboard.getUser().getUserID()!=null && leaderboard.getUser().getUserID()!=null){
                Integer userID= leaderboard.getUser().getUserID();
                UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userID));
                leaderboardEntity.setUser(user);
            }
            if(leaderboard.getIncentivesID()!=null && leaderboard.getIncentivesID().getIncentivesID()!=null){
                Integer incentiveID= leaderboard.getIncentivesID().getIncentivesID();
                IncentivesEntity incentive= incentiveRepository.findById(incentiveID)
                .orElseThrow(() -> new RuntimeException("Incentive not found with id: " + incentiveID));
                leaderboardEntity.setIncentivesID(incentive);
            }
            return leaderboardRepository.save(leaderboardEntity); 
        }).orElseThrow(()-> new RuntimeException("Leaderboard not  found!"));
    }

    @Override
    public List<LeaderboardEntity> findAll() {
        return StreamSupport.stream(leaderboardRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    
    
}
