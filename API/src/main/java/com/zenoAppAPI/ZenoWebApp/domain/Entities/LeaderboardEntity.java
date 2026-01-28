package com.zenoAppAPI.ZenoWebApp.domain.Entities;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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
@Table(name = "Leaderboard")
@Component
public class LeaderboardEntity {

    @Id
    private Integer LeaderboardID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private UserAccountInformationEntity User;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IncentivesID")
    private IncentivesEntity IncentivesID;

    private Integer TotalXP;
}
