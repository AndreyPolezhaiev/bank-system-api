package com.polezhaiev.banksystemapi.service.transaction;

import com.polezhaiev.banksystemapi.dto.transaction.ChangeBalanceResponseDto;
import com.polezhaiev.banksystemapi.dto.transaction.DepositRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.TransferRequestDto;
import com.polezhaiev.banksystemapi.dto.transaction.WithdrawRequestDto;
import com.polezhaiev.banksystemapi.model.BankCard;
import com.polezhaiev.banksystemapi.model.User;
import com.polezhaiev.banksystemapi.repository.BankCardRepository;
import com.polezhaiev.banksystemapi.repository.UserRepository;
import com.polezhaiev.banksystemapi.service.transaction.impl.TransactionServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankCardRepository bankCardRepository;

    @Test
    @DisplayName("""
            Make deposit,
            should return new balance of a user
            """)
    public void deposit_ValidRequestDto_ShouldReturnUpdatedBalance() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setBalance(BigDecimal.valueOf(1000));
        bankCard.setCardNumber("1111111111111111111111111111");

        User user = new User();
        user.setId(1L);
        user.setBankCards(Set.of(bankCard));

        DepositRequestDto requestDto = new DepositRequestDto();
        requestDto.setDepositFounds(BigDecimal.valueOf(200));
        requestDto.setBankCard("1111111111111111111111111111");

        ChangeBalanceResponseDto expected = new ChangeBalanceResponseDto(
                null, "1111111111111111111111111111", BigDecimal.valueOf(1200)
        );

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(bankCardRepository.save(any())).thenReturn(bankCard);

        ChangeBalanceResponseDto actual = transactionService.deposit(requestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make deposit,
            should throw EntityNotFoundException
            """)
    public void deposit_InValidUserEmail_ShouldThrowEntityNotFoundException() {
        DepositRequestDto requestDto = new DepositRequestDto();

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.deposit(new DepositRequestDto())
        );

        String expected = "There is no user with email: " + requestDto.getEmail();
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make deposit,
            should throw EntityNotFoundException
            """)
    public void deposit_InValidCardNumber_ShouldThrowEntityNotFoundException() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setBalance(BigDecimal.valueOf(1000));
        bankCard.setCardNumber("1111111111111111111111111111");

        User user = new User();
        user.setId(1L);
        user.setBankCards(Set.of(bankCard));

        DepositRequestDto requestDto = new DepositRequestDto();
        requestDto.setDepositFounds(BigDecimal.valueOf(200));
        requestDto.setBankCard("22222222222222222222222222222");

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.deposit(requestDto)
        );

        String expected = "Invalid card number: " + requestDto.getBankCard();
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make withdraw,
            should return new balance of a user
            """)
    public void withdraw_ValidRequestDto_ShouldReturnUpdatedBalance() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setBalance(BigDecimal.valueOf(1000));
        bankCard.setCardNumber("1111111111111111111111111111");

        User user = new User();
        user.setId(1L);
        user.setBankCards(Set.of(bankCard));

        WithdrawRequestDto requestDto = new WithdrawRequestDto();
        requestDto.setWithdrawFounds(BigDecimal.valueOf(200));
        requestDto.setBankCard("1111111111111111111111111111");

        ChangeBalanceResponseDto expected = new ChangeBalanceResponseDto(
                null, "1111111111111111111111111111", BigDecimal.valueOf(800)
        );

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
        Mockito.when(bankCardRepository.save(any())).thenReturn(bankCard);

        ChangeBalanceResponseDto actual = transactionService.withdraw(requestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make withdraw,
            should throw EntityNotFoundException
            """)
    public void withdraw_InValidUserEmail_ShouldThrowEntityNotFoundException() {
        WithdrawRequestDto requestDto = new WithdrawRequestDto();

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.withdraw(new WithdrawRequestDto())
        );

        String expected = "There is no user with email: " + requestDto.getEmail();
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make withdraw,
            should throw EntityNotFoundException
            """)
    public void withdraw_InValidCardNumber_ShouldThrowEntityNotFoundException() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setBalance(BigDecimal.valueOf(1000));
        bankCard.setCardNumber("1111111111111111111111111111");

        User user = new User();
        user.setId(1L);
        user.setBankCards(Set.of(bankCard));

        WithdrawRequestDto requestDto = new WithdrawRequestDto();
        requestDto.setWithdrawFounds(BigDecimal.valueOf(200));
        requestDto.setBankCard("22222222222222222222222222222");

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.withdraw(requestDto)
        );

        String expected = "Invalid card number: " + requestDto.getBankCard();
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make withdraw,
            should throw TransactionLackOfMoneyException
            """)
    public void withdraw_LackOfBalance_ShouldThrowTransactionLackOfMoneyException() {
        BankCard bankCard = new BankCard();
        bankCard.setId(1L);
        bankCard.setBalance(BigDecimal.valueOf(1000));
        bankCard.setCardNumber("1111111111111111111111111111");

        User user = new User();
        user.setId(1L);
        user.setBankCards(Set.of(bankCard));

        WithdrawRequestDto requestDto = new WithdrawRequestDto();
        requestDto.setWithdrawFounds(BigDecimal.valueOf(2000));
        requestDto.setBankCard("1111111111111111111111111111");

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.withdraw(requestDto)
        );

        String expected = "Not enough money on the balance to withdraw";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make transfer,
            should correctly transfer money
            """)
    public void transfer_ValidRequestDto_ShouldReturnValidMessage() {
        TransferRequestDto requestDto = new TransferRequestDto();
        requestDto.setBankCardSend("111111111111111111111111111");
        requestDto.setBankCardGet("2222222222222222222222222222");
        requestDto.setTransferFounds(BigDecimal.valueOf(200));
        requestDto.setEmailSend("userSend@gmail.com");
        requestDto.setEmailGet("userGet@gmail.com");

        BankCard bankCardSend = new BankCard();
        bankCardSend.setId(1L);
        bankCardSend.setBalance(BigDecimal.valueOf(1000));
        bankCardSend.setCardNumber("111111111111111111111111111");

        BankCard bankCardGet = new BankCard();
        bankCardGet.setId(2L);
        bankCardGet.setBalance(BigDecimal.valueOf(1000));
        bankCardGet.setCardNumber("2222222222222222222222222222");

        User userSend = new User();
        userSend.setId(1L);
        userSend.setBankCards(Set.of(bankCardSend));
        userSend.setEmail("userSend@gmail.com");

        User userGet = new User();
        userSend.setId(2L);
        userGet.setBankCards(Set.of(bankCardGet));
        userSend.setEmail("userGet@gmail.com");

        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailSend()))).thenReturn(Optional.of(userSend));
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailGet()))).thenReturn(Optional.of(userGet));

        String expected = "Founds "
                + requestDto.getTransferFounds()
                + " from bankCard "
                + bankCardSend.getCardNumber()
                + " were sent to user with email "
                + requestDto.getEmailGet();

        String actual = transactionService.transfer(requestDto).getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make transfer,
            should throw EntityNotFoundException
            """)
    public void transfer_InValidRequestDto_ShouldThrowEntityNotFoundException() {
        TransferRequestDto requestDto = new TransferRequestDto();

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(requestDto)
        );
        String actual = exception.getMessage();
        String expected = "Invalid user's send email: " + requestDto.getEmailSend();

        Assertions.assertEquals(expected, actual);

        requestDto.setEmailSend("email@gmail.com");
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailSend()))).thenReturn(Optional.of(new User()));
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailGet()))).thenReturn(Optional.empty());

        exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(requestDto)
        );

        actual = exception.getMessage();
        expected = "Invalid user's get email: " + requestDto.getEmailGet();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Make transfer,
            should throw EntityNotFoundException because of invalid cards
            """)
    public void transfer_InvalidBankCards_ShouldThrowEntityNotFoundException() {
        TransferRequestDto requestDto = new TransferRequestDto();

        requestDto.setBankCardSend("111111111111111111111111111");
        requestDto.setBankCardGet("2222222222222222222222222222");
        requestDto.setTransferFounds(BigDecimal.valueOf(200));
        requestDto.setEmailSend("userSend@gmail.com");
        requestDto.setEmailGet("userGet@gmail.com");

        BankCard bankCardSend = new BankCard();
        bankCardSend.setId(1L);
        bankCardSend.setBalance(BigDecimal.valueOf(1000));
        bankCardSend.setCardNumber("3333333333333333333333333333");

        BankCard bankCardGet = new BankCard();
        bankCardGet.setId(2L);
        bankCardGet.setBalance(BigDecimal.valueOf(1000));
        bankCardGet.setCardNumber("555555555555555555555555555555");

        User userSend = new User();
        userSend.setId(1L);
        userSend.setBankCards(Set.of(bankCardSend));
        userSend.setEmail("userSend@gmail.com");

        User userGet = new User();
        userSend.setId(2L);
        userGet.setBankCards(Set.of(bankCardGet));
        userSend.setEmail("userGet@gmail.com");

        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailSend()))).thenReturn(Optional.of(userSend));
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailGet()))).thenReturn(Optional.of(userGet));

        Exception exceptionSend = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(requestDto)
        );

        String expectedSend = "Invalid card number of sending user: " + requestDto.getBankCardSend();
        String actualSend = exceptionSend.getMessage();

        Assertions.assertEquals(expectedSend, actualSend);

        bankCardSend.setCardNumber("111111111111111111111111111");
        userSend.setBankCards(Set.of(bankCardSend));

        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailSend()))).thenReturn(Optional.of(userSend));
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailGet()))).thenReturn(Optional.of(userGet));

        Exception exceptionGet = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(requestDto)
        );

        String expectedGet = "Invalid card number of getting user: " + requestDto.getBankCardGet();
        String actualGet = exceptionGet.getMessage();

        Assertions.assertEquals(expectedGet, actualGet);
    }

    @Test
    @DisplayName("""
            Make transfer,
            should throw LackOfMoneyException
            """)
    public void transfer_LackOfMoney_ShouldThrowLackOfMoneyException() {
        TransferRequestDto requestDto = new TransferRequestDto();
        requestDto.setBankCardSend("111111111111111111111111111");
        requestDto.setBankCardGet("2222222222222222222222222222");
        requestDto.setTransferFounds(BigDecimal.valueOf(2000));
        requestDto.setEmailSend("userSend@gmail.com");
        requestDto.setEmailGet("userGet@gmail.com");

        BankCard bankCardSend = new BankCard();
        bankCardSend.setId(1L);
        bankCardSend.setBalance(BigDecimal.valueOf(1000));
        bankCardSend.setCardNumber("111111111111111111111111111");

        BankCard bankCardGet = new BankCard();
        bankCardGet.setId(2L);
        bankCardGet.setBalance(BigDecimal.valueOf(1000));
        bankCardGet.setCardNumber("2222222222222222222222222222");

        User userSend = new User();
        userSend.setId(1L);
        userSend.setBankCards(Set.of(bankCardSend));
        userSend.setEmail("userSend@gmail.com");

        User userGet = new User();
        userSend.setId(2L);
        userGet.setBankCards(Set.of(bankCardGet));
        userSend.setEmail("userGet@gmail.com");

        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailSend()))).thenReturn(Optional.of(userSend));
        Mockito.when(userRepository.findByEmail(eq(requestDto.getEmailGet()))).thenReturn(Optional.of(userGet));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.transfer(requestDto)
        );

        String expected = "Not enough money on the balance to send";
        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }
}
