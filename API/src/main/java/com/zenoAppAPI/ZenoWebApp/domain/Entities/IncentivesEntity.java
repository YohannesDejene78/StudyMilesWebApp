package com.zenoAppAPI.ZenoWebApp.domain.Entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "Incentives")
@Component
public class IncentivesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IncentivesID;

    private Integer EarnedTokens;

    private String WalletAddress;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserAccountInformationEntity UserID;
}
