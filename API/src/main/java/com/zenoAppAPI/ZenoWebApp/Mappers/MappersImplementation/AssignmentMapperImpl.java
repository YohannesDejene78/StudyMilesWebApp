package com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.AssignmentEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.AssignmentFront;

@Component
public class AssignmentMapperImpl implements mapper<AssignmentEntity, AssignmentFront>{

    private ModelMapper modelMapper;

    public AssignmentMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public AssignmentFront mapTo(AssignmentEntity assignmentEntity) {
        return modelMapper.map(assignmentEntity, AssignmentFront.class);
    }

    @Override
    public AssignmentEntity mapFrom(AssignmentFront assignmentFront) {
        return modelMapper.map(assignmentFront, AssignmentEntity.class);
    }
    
}
