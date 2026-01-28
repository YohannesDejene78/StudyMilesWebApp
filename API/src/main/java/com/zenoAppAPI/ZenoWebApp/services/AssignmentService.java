package com.zenoAppAPI.ZenoWebApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.AssignmentEntity;

@Service
public interface AssignmentService {

    AssignmentEntity createAssignment(AssignmentEntity newAssignment, Integer assignmentID);

    Optional<AssignmentEntity> findSpecificAssignment(Integer assignmentID);

    List<AssignmentEntity> findAll();

    boolean isExists(Integer assignmentID);

    AssignmentEntity updateAssignment(AssignmentEntity newAssignment, Integer assignmentID);
    
}
