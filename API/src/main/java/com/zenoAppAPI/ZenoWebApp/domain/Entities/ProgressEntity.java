package com.zenoAppAPI.ZenoWebApp.domain.Entities;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
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
@Table(name = "Progress")
@Component
public class ProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ProgressID_seq")
    private Integer ProgressID;

    @JsonProperty("user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private UserAccountInformationEntity UserID;

    private Integer Percentage;

    private String LessonsCompleted;

    private Integer LessonBreakDown;
    
    private Integer Streak;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IncentivesID")
    private IncentivesEntity IncentivesID; 
}

