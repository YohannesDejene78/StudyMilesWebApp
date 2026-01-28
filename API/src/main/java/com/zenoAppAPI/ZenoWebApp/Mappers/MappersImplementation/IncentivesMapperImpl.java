package com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.IncentivesEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.IncentivesFront;

@Component
public class IncentivesMapperImpl implements mapper<IncentivesEntity, IncentivesFront>{

    private ModelMapper modelMapper;

    public IncentivesMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public IncentivesFront mapTo(IncentivesEntity incentivesEntity) {
        return modelMapper.map(incentivesEntity, IncentivesFront.class);
    }

    @Override
    public IncentivesEntity mapFrom(IncentivesFront incentivesFront) {
        return modelMapper.map(incentivesFront, IncentivesEntity.class);
    }
    
}
