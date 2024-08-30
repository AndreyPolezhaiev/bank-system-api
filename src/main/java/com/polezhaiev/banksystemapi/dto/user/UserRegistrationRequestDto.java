package com.polezhaiev.banksystemapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String phoneNumber;
    @Email
    private String email;
    @NotNull
    private String password;
    @Size(min = 16)
    private String bankCard;
    @NotNull
    @Min(100)
    private BigDecimal initialBalance;
}
