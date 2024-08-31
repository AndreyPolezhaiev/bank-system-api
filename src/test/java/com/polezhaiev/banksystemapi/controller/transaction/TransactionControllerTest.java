package com.polezhaiev.banksystemapi.controller.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polezhaiev.banksystemapi.dto.transaction.ChangeBalanceResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.DepositRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.WithdrawRequestDto;
import com.polezhaiev.banksystemapi.service.transaction.TransactionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    private TransactionService transactionService;

    @Test
    @DisplayName("""
            Make deposit,
            should return changed balance
            """)
    public void deposit_ValidRequest_ShouldReturnValidChangeBalanceResponseDto() throws Exception {
        DepositRequestDto requestDto = new DepositRequestDto();
        requestDto.setEmail("alice@gmail.com");
        requestDto.setBankCard("11111111111111111");
        requestDto.setDepositFounds(BigDecimal.valueOf(1000));

        ChangeBalanceResponseDto responseDto = new ChangeBalanceResponseDto(
                requestDto.getEmail(),
                requestDto.getBankCard(),
                requestDto.getDepositFounds()
        );

        when(transactionService.deposit(any(DepositRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(post("/api/transactions/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("""
            Make withdraw,
            should return changed balance
            """)
    public void withdraw_ValidRequest_ShouldReturnValidChangeBalanceResponseDto() throws Exception {
        WithdrawRequestDto requestDto = new WithdrawRequestDto();
        requestDto.setEmail("alice@gmail.com");
        requestDto.setBankCard("11111111111111111");
        requestDto.setWithdrawFounds(BigDecimal.valueOf(1000));

        ChangeBalanceResponseDto responseDto = new ChangeBalanceResponseDto(
                requestDto.getEmail(),
                requestDto.getBankCard(),
                requestDto.getWithdrawFounds()
        );

        when(transactionService.withdraw(any(WithdrawRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(post("/api/transactions/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("""
            Make transfer,
            should return transfer message
            """)
    public void transfer_ValidRequest_ShouldReturnValidTransferMessage() throws Exception {
        TransferRequestDto requestDto = new TransferRequestDto();
        requestDto.setEmailSend("alice@gmail.com");
        requestDto.setEmailGet("bob@gmail.com");
        requestDto.setTransferFounds(BigDecimal.valueOf(1000));
        requestDto.setBankCardSend("1111111111111111111111111111");
        requestDto.setBankCardGet("2222222222222222222222222222");

        String message = "Founds "
                + requestDto.getTransferFounds()
                + " from bankCard "
                + requestDto.getBankCardSend()
                + " were sent to user with email "
                + requestDto.getEmailGet();

        TransferResponseDto responseDto = new TransferResponseDto();
        responseDto.setMessage(message);

        when(transactionService.transfer(any(TransferRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }
}
