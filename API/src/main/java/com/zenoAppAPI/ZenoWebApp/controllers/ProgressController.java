package com.zenoAppAPI.ZenoWebApp.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.ProgressFront;
import com.zenoAppAPI.ZenoWebApp.services.ProgressService;

@RestController
public class ProgressController {

    private final ProgressService progressService;
    private final mapper<ProgressEntity, ProgressFront> progressMapper;

    public ProgressController(
            ProgressService progressService,
            mapper<ProgressEntity, ProgressFront> progressMapper) {
        this.progressService = progressService;
        this.progressMapper = progressMapper;
    }

    @PostMapping("/progress")
    public ProgressFront createProgress(@RequestBody ProgressFront front) {
        ProgressEntity entity = progressMapper.mapFrom(front);
        return progressMapper.mapTo(progressService.createProgress(entity));
    }

    @PutMapping("/progress/{id}")
    public ResponseEntity<ProgressFront> updateProgress(
            @PathVariable Integer id,
            @RequestBody ProgressFront front) {

        if (!progressService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        front.setProgressID(id);
        ProgressEntity updated = progressService.updateProgress(
                progressMapper.mapFrom(front), id);

        return new ResponseEntity<>(progressMapper.mapTo(updated), HttpStatus.OK);
    }

    @GetMapping("/progress/{id}")
    public ResponseEntity<ProgressFront> fetch(@PathVariable Integer id) {
        Optional<ProgressEntity> progress = progressService.findSpecificProgress(id);
        return progress.map(p ->
                new ResponseEntity<>(progressMapper.mapTo(p), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/progress")
    public List<ProgressFront> all() {
        return progressService.findAll()
                .stream()
                .map(progressMapper::mapTo)
                .collect(Collectors.toList());
    }
}
