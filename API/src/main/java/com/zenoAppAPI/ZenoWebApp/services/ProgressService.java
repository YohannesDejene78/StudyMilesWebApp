package com.zenoAppAPI.ZenoWebApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.ProgressFront;

@Service
public interface ProgressService {
    public ProgressEntity createProgress(ProgressEntity progressEntity);
    public boolean isExists(Integer progressID);
    public ProgressEntity updateProgress(ProgressEntity newProgress, Integer progressID);
    public Optional<ProgressEntity> findSpecificProgress(Integer progressID);
    public List<ProgressEntity> findAll();

}
