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
@Table(name = "Assignment")
@Component
public class AssignmentEntity {
    
    @Id
    private Integer AssignmentID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserID")
    private UserAccountInformationEntity UserID;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "IncentivesID")
    private IncentivesEntity IncentivesID;

    private Integer AssignmentNumber;

    private String Completion;

}
