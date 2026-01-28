package com.zenoAppAPI.ZenoWebApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;

@Repository
public interface IncentiveRepository extends CrudRepository<IncentivesEntity, Integer>{
    
}
