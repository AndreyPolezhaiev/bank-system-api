package com.polezhaiev.banksystemapi.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequestDto {
    @NotNull
    private String email;
    @NotNull
    private String bankCard;
    @NotNull
    @Min(10)
    private BigDecimal depositFounds;
}
