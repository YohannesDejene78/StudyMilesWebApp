package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.repositories.ProgressRepository;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.ProgressService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProgressServiceImpl implements ProgressService {

    private final ProgressRepository progressRepository;
    private final UserAccountInfoRepository userRepo;

    public ProgressServiceImpl(
            ProgressRepository progressRepository,
            UserAccountInfoRepository userRepo) {
        this.progressRepository = progressRepository;
        this.userRepo = userRepo;
    }

    @Override
    public ProgressEntity createProgress(ProgressEntity progress) {
        Integer userID = progress.getUserID().getUserID();
        UserAccountInformationEntity user = userRepo.findById(userID)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        progress.setUserID(user);
        return progressRepository.save(progress);
    }

    @Override
    public ProgressEntity updateProgress(ProgressEntity progress, Integer id) {
        return progressRepository.findById(id).map(existing -> {
            existing.setLessonsCompleted(progress.getLessonsCompleted());
            existing.setLessonBreakDown(progress.getLessonBreakDown());
            existing.setPercentage(progress.getPercentage());
            existing.setStreak(progress.getStreak());
            return progressRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Progress not found"));
    }

    @Override
    public Optional<ProgressEntity> findSpecificProgress(Integer id) {
        return progressRepository.findById(id);
    }

    @Override
    public List<ProgressEntity> findAll() {
        return StreamSupport.stream(progressRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Integer id) {
        return progressRepository.existsById(id);
    }
}
