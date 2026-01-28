package com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.ProgressEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.ProgressFront;

@Component
public class ProgressMapperImpl implements mapper<ProgressEntity, ProgressFront>{

    private ModelMapper modelMapper;

    public ProgressMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public ProgressFront mapTo(ProgressEntity progressEntity) {
        return modelMapper.map(progressEntity, ProgressFront.class);
    }

    @Override
    public ProgressEntity mapFrom(ProgressFront progressFront) {
        return modelMapper.map(progressFront, ProgressEntity.class);
    }
    
}
