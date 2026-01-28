package com.zenoAppAPI.ZenoWebApp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.AssignmentEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.AssignmentFront;
import com.zenoAppAPI.ZenoWebApp.services.AssignmentService;

@RestController
public class AssignmentController {
    
    private mapper<AssignmentEntity, AssignmentFront> assignmentMapper;
    private AssignmentEntity assignmentEntity;
    private AssignmentService assignmentService;

    public AssignmentController(mapper<AssignmentEntity, AssignmentFront> assignmentMapper, AssignmentEntity assignmentEntity,
    AssignmentService assignmentService){
        this.assignmentEntity=assignmentEntity;
        this.assignmentMapper=assignmentMapper;
        this.assignmentService=assignmentService;
    }

    @PutMapping(path = "/assignment/create")
    public ResponseEntity<AssignmentFront> createAssignment(@RequestBody AssignmentFront assignmentFront){
        Integer assignmentID= assignmentFront.getAssignmentID();
        AssignmentEntity newAssignment= assignmentMapper.mapFrom(assignmentFront);
        AssignmentEntity savedAssignmentEntity= assignmentService.createAssignment(newAssignment, assignmentID);
        return new ResponseEntity<>(assignmentMapper.mapTo(savedAssignmentEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/assignment/{AssignmentID}")
    public ResponseEntity<AssignmentFront> fetchSpecificAssignment(@PathVariable("AssignmentID") Integer assignmentID){
        Optional<AssignmentEntity> specificAssignment= assignmentService.findSpecificAssignment(assignmentID);
        return specificAssignment.map(assignmentEntity->{
            AssignmentFront assignmentFront= assignmentMapper.mapTo(assignmentEntity);
            return new ResponseEntity<>(assignmentFront, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/assignment")
    public List<AssignmentFront> displayAssignment(){
        List<AssignmentEntity> listOfAssignments= assignmentService.findAll();
        return listOfAssignments.stream().map(assignmentMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/assignment/{AssignmentID}")
    public ResponseEntity<AssignmentFront> updateAssignment(@PathVariable("AssignmentID") Integer assignmentID, @RequestBody AssignmentFront assignmentFront){
        if(!assignmentService.isExists(assignmentID)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        assignmentFront.setAssignmentID(assignmentID);
        AssignmentEntity newAssignment= assignmentMapper.mapFrom(assignmentFront);
        AssignmentEntity updatedAssignmentEntity= assignmentService.updateAssignment(newAssignment, assignmentID);
        return new ResponseEntity<>(assignmentMapper.mapTo(updatedAssignmentEntity), HttpStatus.OK);
    }

}
