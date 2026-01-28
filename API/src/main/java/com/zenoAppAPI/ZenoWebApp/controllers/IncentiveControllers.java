package com.zenoAppAPI.ZenoWebApp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.IncentivesFront;
import com.zenoAppAPI.ZenoWebApp.services.IncentiveService;

@RestController
public class IncentiveControllers {
    
    private IncentiveService incentiveService;
    private mapper<IncentivesEntity, IncentivesFront> incentiveMapper;
    private IncentivesEntity incentivesEntity;

    public IncentiveControllers(IncentiveService incentiveService, mapper<IncentivesEntity, IncentivesFront> incentiveMapper, IncentivesEntity incentivesEntity){
        this.incentiveMapper=incentiveMapper;
        this.incentiveService=incentiveService;
        this.incentivesEntity=incentivesEntity;
    }

    @PostMapping(path = "/incentive")
    public IncentivesFront createIncentive(@RequestBody IncentivesFront incentivesFront){
        IncentivesEntity newIncentivesEntity= incentiveMapper.mapFrom(incentivesFront);
        IncentivesEntity savedIncentivesEntity= incentiveService.createIncentive(newIncentivesEntity);
        return incentiveMapper.mapTo(savedIncentivesEntity);
    }

    @PutMapping(path = "/incentive/{IncentiveID}")
    public ResponseEntity<IncentivesFront> updateIncentive(@PathVariable("IncentiveID") Integer incentiveID, @RequestBody IncentivesFront incentivesFront){
        if(!incentiveService.isExists(incentiveID)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        incentivesFront.setIncentivesID(incentiveID);
        IncentivesEntity incentive= incentiveMapper.mapFrom(incentivesFront);
        IncentivesEntity updatedIncentivesEntity= incentiveService.updateIncentive(incentive, incentiveID);
        return new ResponseEntity<>(incentiveMapper.mapTo(updatedIncentivesEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/incentive/{IncentiveID}")
    public ResponseEntity<IncentivesFront> fetchSpecificIncentive(@PathVariable("IncentiveID")Integer incentiveID){
        Optional<IncentivesEntity> specificIncentive= incentiveService.findSpecificIncentive(incentiveID);
        return specificIncentive.map(incentivesEntity->{
            IncentivesFront incentivesFront= incentiveMapper.mapTo(incentivesEntity);
            return new ResponseEntity<>(incentivesFront, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/incentive")
    public List<IncentivesFront> displayIncentives(){
        List<IncentivesEntity> listOfIncentives= incentiveService.findAll();
        return listOfIncentives.stream().map(incentiveMapper::mapTo).collect(Collectors.toList());
    }


}
