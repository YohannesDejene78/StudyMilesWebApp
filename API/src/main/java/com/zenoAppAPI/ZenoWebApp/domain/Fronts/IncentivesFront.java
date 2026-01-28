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
public class IncentivesFront {
    
    private Integer IncentivesID;

    private Integer EarnedTokens;

    private UserAccountInformationFront UserID;

    private String WalletAddress;

}

