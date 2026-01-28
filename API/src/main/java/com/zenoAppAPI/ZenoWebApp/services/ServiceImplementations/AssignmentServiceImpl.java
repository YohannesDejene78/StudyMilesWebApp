package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.AssignmentEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.repositories.AssignmentRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.IncentiveRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.AssignmentService;
import jakarta.persistence.EntityNotFoundException;


@Service
public class AssignmentServiceImpl implements AssignmentService{

    private AssignmentRepository assignmentRepository;
    private AssignmentEntity assignmentEntity;
    private UserAccountInfoRepository userAccountInfoRepository;
    private IncentiveRepository incentiveRepository;

    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentEntity assignmentEntity,
    UserAccountInfoRepository userAccountInfoRepository, IncentiveRepository incentiveRepository){
        this.assignmentRepository=assignmentRepository;
        this.assignmentEntity=assignmentEntity;
        this.userAccountInfoRepository=userAccountInfoRepository;
        this.incentiveRepository=incentiveRepository;
    }

    @Override
    public AssignmentEntity createAssignment(AssignmentEntity newAssignment, Integer assignmentID) {
        Integer userID= newAssignment.getUserID().getUserID();
        UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
        .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userID));
        newAssignment.setUserID(user);
        Integer incentiveID=newAssignment.getIncentivesID().getIncentivesID();
        IncentivesEntity incentive= incentiveRepository.findById(incentiveID)
        .orElseThrow(() -> new EntityNotFoundException("Incentive not found with ID " + incentiveID));
        newAssignment.setIncentivesID(incentive);
        newAssignment.setAssignmentID(assignmentID);
        return assignmentRepository.save(newAssignment);
    }

    @Override
    public Optional<AssignmentEntity> findSpecificAssignment(Integer assignmentID) {
        return assignmentRepository.findById(assignmentID);
    }

    @Override
    public List<AssignmentEntity> findAll() {
        return StreamSupport.stream(assignmentRepository.findAll()
        .spliterator(), 
        false)
        .collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Integer assignmentID) {
        return assignmentRepository.existsById(assignmentID);
    }

    @Override
    public AssignmentEntity updateAssignment(AssignmentEntity newAssignment, Integer assignmentID) {
        return assignmentRepository.findById(assignmentID).map(assignmentEntity->{
            assignmentEntity.setAssignmentNumber(newAssignment.getAssignmentNumber());
            assignmentEntity.setCompletion(newAssignment.getCompletion());
            //assignmentEntity.setIncentives(newAssignment.getIncentives());
            if(newAssignment.getUserID()!=null && newAssignment.getUserID().getUserID()!=null){
                Integer userID= newAssignment.getUserID().getUserID();
                UserAccountInformationEntity user= userAccountInfoRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userID));
                assignmentEntity.setUserID(user);
            }
            return assignmentRepository.save(assignmentEntity);
        }).orElseThrow(()-> new RuntimeException("Assignment not  found!"));
    }
    
}
