package com.polezhaiev.banksystemapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDetailedInfoRequestDto {
    @NotNull
    private String phoneNumber;
}
