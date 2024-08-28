package com.polezhaiev.banksystemapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @Email
    private String email;
    private String password;
    @Size(min = 16)
    private String bankCard;
    @NotNull
    @Min(100)
    private BigDecimal initialBalance;
}
