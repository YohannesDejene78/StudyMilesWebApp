package com.zenoAppAPI.ZenoWebApp.services.ServiceImplementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import java.util.stream.StreamSupport;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.repositories.UserAccountInfoRepository;
import com.zenoAppAPI.ZenoWebApp.services.UserAccountInfoService;

@Service
public class UserAccountInfoServiceImpl implements UserAccountInfoService{

    private UserAccountInfoRepository userRepository;
    private UserAccountInformationEntity userEntity;

    public UserAccountInfoServiceImpl(UserAccountInfoRepository userRepository, UserAccountInformationEntity userEntity){
        this.userRepository=userRepository;
        this.userEntity=userEntity;
    }

    @Override
    public UserAccountInformationEntity createUser(UserAccountInformationEntity user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<UserAccountInformationEntity> findSpecificUser(Integer userID) {
        return userRepository.findById(userID);
    }

    @Override
    public List<UserAccountInformationEntity> findAll() {
        return StreamSupport.stream(userRepository.findAll()
        .spliterator(),
        false)
        .collect(Collectors.toList());
    }

    @Override
    public boolean isExists(Integer userID) {
        return userRepository.existsById(userID);
    }

    @Override
    public UserAccountInformationEntity updateUser(UserAccountInformationEntity newUserInfo, Integer userID) {
        return userRepository.findById(userID).map(userEntity->{
            userEntity.setName(newUserInfo.getName());
            userEntity.setUsername(newUserInfo.getUsername());
            userEntity.setPassword(newUserInfo.getPassword());
            return userRepository.save(userEntity);
        }).orElseThrow(()-> new RuntimeException("User not  found!"));
    }
    
    
}
