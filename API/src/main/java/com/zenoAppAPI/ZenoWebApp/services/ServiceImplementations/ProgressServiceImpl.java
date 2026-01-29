package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.ProgressFront;
import com.zenoAppAPI.ZenoWebApp.repositories.IncentiveRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.ProgressRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.ProgressService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProgressServiceImpl implements ProgressService{

    private ProgressEntity progressEntity;
    private ProgressRepository progressRepository;
    private UserAccountInfoRepository userRepository;
    private IncentiveRepository incentiveRepository;

    public ProgressServiceImpl(ProgressEntity progressEntity, ProgressRepository progressRepository, 
    UserAccountInfoRepository userRepository, IncentiveRepository incentiveRepository){
        this.progressEntity=progressEntity;
        this.progressRepository=progressRepository;
        this.userRepository=userRepository;
        this.incentiveRepository=incentiveRepository;
    }

    @Override
    public ProgressEntity createProgress(ProgressEntity progressEntity) {
        Integer userId =progressEntity.getUserID().getUserID();
        UserAccountInformationEntity userEntity=userRepository.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));
        progressEntity.setUserID(userEntity);

        Integer incentiveID= progressEntity.getIncentivesID().getIncentivesID();
        IncentivesEntity incentivesEntity=incentiveRepository
            .findById(incentiveID)
            .orElseThrow(() -> new EntityNotFoundException("Incentive not found with ID " + incentiveID));
        progressEntity.setIncentivesID(incentivesEntity);
        return progressRepository.save(progressEntity);
    }

    

    @Override
    public boolean isExists(Integer progressID) {
        return progressRepository.existsById(progressID);
    }

    @Override
    public ProgressEntity updateProgress(ProgressEntity newProgress, Integer progressID) {
        return progressRepository.findById(progressID).map(progressEntity->{
            progressEntity.setIncentivesID(newProgress.getIncentivesID());
            progressEntity.setLessonBreakDown(newProgress.getLessonBreakDown());
            progressEntity.setLessonsCompleted(newProgress.getLessonsCompleted());
            progressEntity.setPercentage(newProgress.getPercentage());
            progressEntity.setStreak(newProgress.getStreak());
            if (newProgress.getUserID() != null && newProgress.getUserID().getUserID() != null) {
                Integer userId = newProgress.getUserID().getUserID();
                UserAccountInformationEntity user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
                progressEntity.setUserID(user);  
            }            
            return progressRepository.save(progressEntity);
        }).orElseThrow(()-> new RuntimeException("Progress not  found!"));
    }

    @Override
    public Optional<ProgressEntity> findSpecificProgress(Integer progressID) {
        return progressRepository.findById(progressID);
    }

    @Override
    public List<ProgressEntity> findAll() {
        return StreamSupport.stream(progressRepository.findAll()
        .spliterator(),
        false)
        .collect(Collectors.toList());
    }
    
}
