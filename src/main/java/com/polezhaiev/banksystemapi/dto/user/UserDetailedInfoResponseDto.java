package com.polezhaiev.banksystemapi.dto.user;

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
    private Map<String, BigDecimal> cardsBalance;
}
