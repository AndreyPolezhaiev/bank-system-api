package com.polezhaiev.banksystemapi.controller.transaction;

import com.polezhaiev.banksystemapi.dto.transaction.ChangeBalanceResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.DepositRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.WithdrawRequestDto;
import com.polezhaiev.banksystemapi.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Bank system api", description = "Endpoints for transaction controller")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/deposit")
    @Operation(summary = "Deposit founds", description = "Deposit founds")
    public ChangeBalanceResponseDto deposit(@RequestBody @Valid DepositRequestDto requestDto) {
        return transactionService.deposit(requestDto);
    }

    @PostMapping("/withdraw")
    @Operation(summary = "Withdraw founds", description = "Withdraw founds")
    public ChangeBalanceResponseDto withdraw(@RequestBody @Valid WithdrawRequestDto requestDto) {
        return transactionService.withdraw(requestDto);
    }

    @PostMapping("/transfer")
    @Operation(summary = "Transfer founds", description = "Transfer founds")
    public TransferResponseDto transfer(@RequestBody @Valid TransferRequestDto requestDto) {
        return transactionService.transfer(requestDto);
    }
}
