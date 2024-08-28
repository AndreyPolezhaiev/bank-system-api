package com.polezhaiev.banksystemapi.dto;

import java.math.BigDecimal;
import java.util.Map;
import lombok.Data;

@Data
public class UserDetailedInfoResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Map<String, BigDecimal> cardsBalance;
}
