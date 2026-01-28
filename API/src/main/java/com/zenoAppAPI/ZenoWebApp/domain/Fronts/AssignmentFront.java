package com.zenoAppAPI.ZenoWebApp.domain.Fronts;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Component
public class AssignmentFront {

    private Integer AssignmentID;

    private UserAccountInformationFront UserID;

    private IncentivesFront IncentivesID;

    private Integer AssignmentNumber;

    private String Completion;

}

