package com.polezhaiev.banksystemapi.service.transaction.impl;

import com.polezhaiev.banksystemapi.dto.transaction.ChangeBalanceResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.DepositRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.WithdrawRequestDto;
import com.polezhaiev.banksystemapi.model.BankCard;
import com.polezhaiev.banksystemapi.model.User;
import com.polezhaiev.banksystemapi.repository.BankCardRepository;
import com.polezhaiev.banksystemapi.repository.UserRepository;
import com.polezhaiev.banksystemapi.service.transaction.TransactionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final UserRepository userRepository;
    private final BankCardRepository bankCardRepository;

    @Override
    @Transactional
    public ChangeBalanceResponseDto deposit(DepositRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isEmpty()) {
            throw new EntityNotFoundException("There is no user with email: " + requestDto.getEmail());
        }

        User user = userRepository.findByEmail(requestDto.getEmail()).get();
        BankCard bankCard = user.getBankCards()
                .stream()
                .filter(c -> c.getCardNumber().equals(requestDto.getBankCard()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException(
                                "Invalid card number: " + requestDto.getBankCard())
                );

        bankCard.setBalance(bankCard.getBalance().add(requestDto.getDepositFounds()));
        BankCard updatedCard = bankCardRepository.save(bankCard);

        return new ChangeBalanceResponseDto(user.getEmail(), updatedCard.getBalance());
    }

    @Override
    @Transactional
    public ChangeBalanceResponseDto withdraw(WithdrawRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isEmpty()) {
            throw new EntityNotFoundException("There is no user with email: " + requestDto.getEmail());
        }

        User user = userRepository.findByEmail(requestDto.getEmail()).get();
        BankCard bankCard = user.getBankCards()
                .stream()
                .filter(c -> c.getCardNumber().equals(requestDto.getBankCard()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException(
                                "Invalid card number: " + requestDto.getBankCard())
                );

        bankCard.setBalance(bankCard.getBalance().subtract(requestDto.getWithdrawFounds()));
        BankCard updatedCard = bankCardRepository.save(bankCard);

        return new ChangeBalanceResponseDto(user.getEmail(), updatedCard.getBalance());
    }

    @Override
    public TransferResponseDto transfer(TransferRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmailSend()).isEmpty()) {
            throw new EntityNotFoundException("Invalid user's send email: " + requestDto.getEmailSend());
        }

        if (userRepository.findByEmail(requestDto.getEmailGet()).isEmpty()) {
            throw new EntityNotFoundException("Invalid user's get email: " + requestDto.getEmailGet());
        }

        User userSend = userRepository.findByEmail(requestDto.getEmailSend()).get();
        User userGet = userRepository.findByEmail(requestDto.getEmailGet()).get();

        BankCard bankCardSend = userSend.getBankCards()
                .stream()
                .filter(c -> c.getCardNumber().equals(requestDto.getBankCardSend()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException(
                                "Invalid card number of sending user: " + requestDto.getBankCardSend())
                );

        BankCard bankCardGet = userGet.getBankCards()
                .stream()
                .filter(c -> c.getCardNumber().equals(requestDto.getBankCardGet()))
                .findFirst().orElseThrow(
                        () -> new EntityNotFoundException(
                                "Invalid card number of getting user: " + requestDto.getBankCardGet())
                );

        bankCardSend.setBalance(bankCardSend.getBalance().subtract(requestDto.getTransferFounds()));
        bankCardGet.setBalance(bankCardGet.getBalance().add(requestDto.getTransferFounds()));

        bankCardRepository.save(bankCardSend);
        bankCardRepository.save(bankCardGet);

        TransferResponseDto responseDto = new TransferResponseDto();
        String message = "Founds "
                + requestDto.getTransferFounds()
                + " were sent to user with email "
                + requestDto.getEmailGet();
        responseDto.setMessage(message);

        return responseDto;
    }
}
