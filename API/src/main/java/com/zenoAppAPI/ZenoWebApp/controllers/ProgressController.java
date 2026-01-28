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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation.ProgressMapperImpl;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.ProgressFront;
import com.zenoAppAPI.ZenoWebApp.services.ProgressService;

@RestController
public class ProgressController {
    
    private ProgressEntity progressEntity;
    private mapper<ProgressEntity, ProgressFront> progressMapper;
    private ProgressService progressService;

    public ProgressController(ProgressEntity progressEntity, mapper<ProgressEntity, ProgressFront> progressMapper,
    ProgressService progressService){
        this.progressEntity=progressEntity;
        this.progressMapper=progressMapper;
        this.progressService=progressService;
    }



    @PostMapping(path = "/progress")
    public ProgressFront createProgress(@RequestBody ProgressFront progressFront){
        ProgressEntity newProgress= progressMapper.mapFrom(progressFront);
        ProgressEntity updatedProgressEntity= progressService.createProgress(newProgress);
        return progressMapper.mapTo(updatedProgressEntity);
    }
    

    @PutMapping(path = "/progress/{ProgressID}")
    public ResponseEntity<ProgressFront> updateProgress(@PathVariable("ProgressID") Integer ProgressID, @RequestBody ProgressFront progressFront){
        if(!progressService.isExists(ProgressID)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        progressFront.setProgressID(ProgressID);
        ProgressEntity newProgress= progressMapper.mapFrom(progressFront);
        ProgressEntity updatedEntity=progressService.updateProgress(newProgress, ProgressID);
        return new ResponseEntity<>(progressMapper.mapTo(updatedEntity), HttpStatus.OK);

    }

    @GetMapping(path = "/progress/{ProgressID}")
    public ResponseEntity<ProgressFront> fetchSpecificProgress(@PathVariable("ProgressID") Integer ProgressID){
        Optional<ProgressEntity> specificProgress= progressService.findSpecificProgress(ProgressID);
        return specificProgress.map(progressEntity->{
            ProgressFront progressFront= progressMapper.mapTo(progressEntity);
            return new ResponseEntity<>(progressFront, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/progress")
    public List<ProgressFront> displayProgress(){
        List<ProgressEntity> listOfProgress= progressService.findAll();
        return listOfProgress.stream().map(progressMapper::mapTo).collect(Collectors.toList());
    }

}
