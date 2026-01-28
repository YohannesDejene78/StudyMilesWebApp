package com.zenoAppAPI.ZenoWebApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.LeaderboardEntity;

@Service
public interface LeaderboardService {
    
    LeaderboardEntity createLeaderboard(LeaderboardEntity newLeaderboardEntity, Integer leaderboardID);

    Optional<LeaderboardEntity> findSpecificLeaderboard(Integer leaderboardID);

    boolean isExists(Integer leaderboardID);
    LeaderboardEntity updateLeaderboard(LeaderboardEntity leaderboard, Integer leaderboardID);

    List<LeaderboardEntity> findAll();
    
}
