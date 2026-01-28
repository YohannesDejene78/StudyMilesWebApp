package com.zenoAppAPI.ZenoWebApp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.zenoAppAPI.ZenoWebApp.domain.Entities.UserAccountInformationEntity;

@Repository
public interface UserAccountInfoRepository extends CrudRepository<UserAccountInformationEntity, Integer>{
    
}
