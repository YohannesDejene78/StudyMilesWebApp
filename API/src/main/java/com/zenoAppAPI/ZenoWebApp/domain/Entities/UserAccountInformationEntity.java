package com.zenoAppAPI.ZenoWebApp.domain.Entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "UserAccountInformation")
@Component
public class UserAccountInformationEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserID_seq")
    private Integer UserID;

    private String Name;

    private String Username;

    private String Password;

}