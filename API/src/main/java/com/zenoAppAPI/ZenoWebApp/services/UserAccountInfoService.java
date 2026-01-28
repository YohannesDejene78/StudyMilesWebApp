package com.zenoAppAPI.ZenoWebApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;

@Service
public interface UserAccountInfoService {
    public UserAccountInformationEntity createUser(UserAccountInformationEntity user);

    public Optional<UserAccountInformationEntity> findSpecificUser(Integer userID);

    public List<UserAccountInformationEntity> findAll();

    public boolean isExists(Integer userID);

    public UserAccountInformationEntity updateUser(UserAccountInformationEntity newUserInfo, Integer userID);
}
