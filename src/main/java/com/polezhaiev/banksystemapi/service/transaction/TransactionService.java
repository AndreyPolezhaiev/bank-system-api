package com.polezhaiev.banksystemapi.service.transaction;

import com.polezhaiev.banksystemapi.dto.transaction.ChangeBalanceResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.DepositRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.WithdrawRequestDto;

public interface TransactionService {
    ChangeBalanceResponseDto deposit(DepositRequestDto requestDto);

    ChangeBalanceResponseDto withdraw(WithdrawRequestDto requestDto);

    TransferResponseDto transfer(TransferRequestDto requestDto);
}
