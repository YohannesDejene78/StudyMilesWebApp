package com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.UserAccountInformationFront;

@Component
public class UserInfoMapperImpl implements mapper<UserAccountInformationEntity, UserAccountInformationFront>{

    private ModelMapper modelMapper;

    public UserInfoMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public UserAccountInformationFront mapTo(UserAccountInformationEntity userInfoEntity) {
        return modelMapper.map(userInfoEntity, UserAccountInformationFront.class);
    }

    @Override
    public UserAccountInformationEntity mapFrom(UserAccountInformationFront userInfoFront) {
        return modelMapper.map(userInfoFront, UserAccountInformationEntity.class);
    }
    
}
