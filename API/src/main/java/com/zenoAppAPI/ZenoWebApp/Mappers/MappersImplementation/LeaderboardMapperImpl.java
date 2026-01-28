package com.zenoAppAPI.ZenoWebApp.Mappers.MappersImplementation;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.zenoAppAPI.ZenoWebApp.Mappers.mapper;
import com.zenoAppAPI.ZenoWebApp.domain.Entities.LeaderboardEntity;
import com.zenoAppAPI.ZenoWebApp.domain.Fronts.LeaderboardFront;

@Component
public class LeaderboardMapperImpl implements mapper<LeaderboardEntity, LeaderboardFront>{

    private ModelMapper modelMapper;

    public LeaderboardMapperImpl(ModelMapper modelMapper){
        this.modelMapper=modelMapper;
    }

    @Override
    public LeaderboardFront mapTo(LeaderboardEntity leaderboardEntity) {
        return modelMapper.map(leaderboardEntity, LeaderboardFront.class);
    }

    @Override
    public LeaderboardEntity mapFrom(LeaderboardFront leaderboardFront) {
        return modelMapper.map(leaderboardFront, LeaderboardEntity.class);
    }
    
}
