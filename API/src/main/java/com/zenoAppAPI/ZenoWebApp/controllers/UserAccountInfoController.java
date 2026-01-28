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
import com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation.UserInfoMapperImpl;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.UserAccountInformationFront;
import com.zenoAppAPI.ZenoWebApp.services.UserAccountInfoService;

@RestController
public class UserAccountInfoController {
    
    private UserAccountInfoService userServices;
    private UserAccountInformationEntity userInfoEntity;
    private mapper<UserAccountInformationEntity, UserAccountInformationFront> userMapper;

    public UserAccountInfoController(UserAccountInformationEntity userInfoEntity, UserAccountInfoService userServices,
    mapper<UserAccountInformationEntity, UserAccountInformationFront> userMapper){
        this.userInfoEntity=userInfoEntity;
        this.userServices=userServices;
        this.userMapper=userMapper;
    }

    @PostMapping(path = "/new_user")
    public UserAccountInformationFront createUser(@RequestBody UserAccountInformationFront newUser){
        UserAccountInformationEntity userEntity= userMapper.mapFrom(newUser);
        UserAccountInformationEntity savedUserEntity= userServices.createUser(userEntity);
        return userMapper.mapTo(savedUserEntity);
    }

    @GetMapping(path = "/new_user/{UserID}")
    public ResponseEntity<UserAccountInformationFront> fetchSpecificUser(@PathVariable("UserID")Integer UserID){
        Optional<UserAccountInformationEntity> specificUser= userServices.findSpecificUser(UserID);
        return specificUser.map(userInfoEntity->{
            UserAccountInformationFront userFront= userMapper.mapTo(userInfoEntity);
            return new ResponseEntity<>(userFront, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        
    }
    
    @GetMapping(path = "/new_user")
    public List<UserAccountInformationFront> displayUsers(){
        List<UserAccountInformationEntity> listOfUsers= userServices.findAll();
        return listOfUsers.stream().map(userMapper::mapTo).collect(Collectors.toList());
    }

    @PutMapping(path = "/new_user/{UserID}")
    public ResponseEntity<UserAccountInformationFront> updateUserInfo(@PathVariable("UserID") Integer UserID, @RequestBody UserAccountInformationFront userFront){
        if(!userServices.isExists(UserID)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userFront.setUserID(UserID);
        UserAccountInformationEntity newUserInfo= userMapper.mapFrom(userFront);
        UserAccountInformationEntity updatedUserInfo= userServices.updateUser(newUserInfo, UserID);
        return new ResponseEntity<>(userMapper.mapTo(updatedUserInfo), HttpStatus.OK);
    }

}
