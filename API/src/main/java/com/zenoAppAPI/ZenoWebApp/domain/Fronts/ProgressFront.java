package com.zenoAppAPI.ZenoWebApp.domain.Fronts;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgressFront {

    private Integer ProgressID;
    private UserAccountInformationFront UserID;
    private Integer[] Percentage;
    private Integer LessonsCompleted;
    private Integer LessonBreakDown;
    private Integer Streak;
}
