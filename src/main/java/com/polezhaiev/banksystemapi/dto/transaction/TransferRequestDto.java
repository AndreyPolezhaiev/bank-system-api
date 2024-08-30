package com.polezhaiev.banksystemapi.dto.transaction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class TransferRequestDto {
    @NotNull
    @Min(10)
    private BigDecimal transferFounds;
    @NotNull
    private String emailSend;
    @NotNull
    private String bankCardSend;
    @NotNull
    private BigDecimal depositFoundsSend;
    @NotNull
    private String emailGet;
    @NotNull
    private String bankCardGet;
    @NotNull
    private BigDecimal depositFoundsGet;
}
