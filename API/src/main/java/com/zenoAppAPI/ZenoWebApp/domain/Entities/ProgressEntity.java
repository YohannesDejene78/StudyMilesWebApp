package com.zenoAppAPI.ZenoWebApp.domain.Entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Progress")
public class ProgressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ProgressID;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private UserAccountInformationEntity UserID;

    @Column(columnDefinition = "integer[]")
    private Integer[] Percentage;

    private Integer LessonsCompleted;
    private Integer LessonBreakDown;
    private Integer Streak;
}
