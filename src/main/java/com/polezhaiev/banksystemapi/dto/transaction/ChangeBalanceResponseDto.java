package com.polezhaiev.banksystemapi.dto.transaction;

import java.math.BigDecimal;

public record ChangeBalanceResponseDto(String email, String bankCard, BigDecimal balance) {
}
