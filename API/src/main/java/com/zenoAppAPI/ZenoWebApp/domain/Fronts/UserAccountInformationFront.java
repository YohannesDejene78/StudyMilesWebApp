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
public class UserAccountInformationFront {
    
    private Integer UserID;

    private String Name;

    private String Username;

    private String Password;

}

