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
import com.zenoAppAPI.ZenoWebApp.domain.Entities.LeaderboardEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.LeaderboardFront;
import com.zenoAppAPI.ZenoWebApp.services.LeaderboardService;

@RestController
public class LeaderboardController {

    private LeaderboardService leaderboardService;
    private LeaderboardEntity leaderboardEntity;
    private mapper<LeaderboardEntity, LeaderboardFront> leaderboardMapper;

    public LeaderboardController(LeaderboardService leaderboardService, LeaderboardEntity leaderboardEntity,
    mapper<LeaderboardEntity, LeaderboardFront> leaderboardMapper){
        this.leaderboardEntity=leaderboardEntity;
        this.leaderboardMapper=leaderboardMapper;
        this.leaderboardService=leaderboardService;
    }

    @PutMapping(path = "/leaderboard/create")
    public ResponseEntity<LeaderboardFront> createLeaderbord(@RequestBody LeaderboardFront leaderboardFront){
        Integer leaderboardID= leaderboardFront.getLeaderboardID();
        LeaderboardEntity newLeaderboardEntity= leaderboardMapper.mapFrom(leaderboardFront);
        LeaderboardEntity savLeaderboardEntity= leaderboardService.createLeaderboard(newLeaderboardEntity, leaderboardID);
        return new ResponseEntity<>(leaderboardMapper.mapTo(savLeaderboardEntity), HttpStatus.OK);
    }

    @PutMapping(path = "/leaderboard/{LeaderboardID}")
    public ResponseEntity<LeaderboardFront> updateLeaderboard(@PathVariable("LeaderboardID") Integer leaderboardID, @RequestBody LeaderboardFront leaderboardFront){
        if(!leaderboardService.isExists(leaderboardID)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        leaderboardFront.setLeaderboardID(leaderboardID);
        LeaderboardEntity leaderboard= leaderboardMapper.mapFrom(leaderboardFront);
        LeaderboardEntity updatedLeaderboardEntity= leaderboardService.updateLeaderboard(leaderboard, leaderboardID);
        return new ResponseEntity<>(leaderboardMapper.mapTo(updatedLeaderboardEntity), HttpStatus.OK);
    }

    @GetMapping(path = "/leaderboard/{LeaderboardID}")
    public ResponseEntity<LeaderboardFront> fetchSpecificLeaderboard(@PathVariable("LeaderboardID") Integer leaderboardID){
        Optional<LeaderboardEntity> specificLeaderboard= leaderboardService.findSpecificLeaderboard(leaderboardID);
        return specificLeaderboard.map(leaderboardEntity->{
            LeaderboardFront leaderboardFront= leaderboardMapper.mapTo(leaderboardEntity);
            return new ResponseEntity<>(leaderboardFront, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/leaderboard")
    public List<LeaderboardFront> displayLeaderboard(){
        List<LeaderboardEntity> listOfLeaderboard= leaderboardService.findAll();
        return listOfLeaderboard.stream().map(leaderboardMapper::mapTo).collect(Collectors.toList());
    }
    
}
