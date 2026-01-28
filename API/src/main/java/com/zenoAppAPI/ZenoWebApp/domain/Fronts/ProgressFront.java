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
public class ProgressFront {
    
    private Integer ProgressID;

    private UserAccountInformationFront UserID;

    private Integer Percentage;

    private String LessonsCompleted;

    private Integer LessonBreakDown;

    private Integer Streak;

    private IncentivesFront IncentivesID;

}
