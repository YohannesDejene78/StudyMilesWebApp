package com.zenoAppAPI.ZenoWebApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.LeaderboardEntity;

@Repository
public interface LeaderboardRepository extends CrudRepository<LeaderboardEntity, Integer>{
    
}
