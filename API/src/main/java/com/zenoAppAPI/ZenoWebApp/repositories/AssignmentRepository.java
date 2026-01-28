package com.zenoAppAPI.ZenoWebApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.AssignmentEntity;

@Repository
public interface AssignmentRepository extends CrudRepository<AssignmentEntity, Integer>{
    
}
