package com.zenoAppAPI.ZenoWebApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;

@Repository
public interface ProgressRepository extends CrudRepository<ProgressEntity, Integer>{
    
}
