package com.polezhaiev.banksystemapi.service.user;

import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserResponseDto;
import com.polezhaiev.banksystemapi.mapper.UserMapper;
import com.polezhaiev.banksystemapi.model.BankCard;
import com.polezhaiev.banksystemapi.model.User;
import com.polezhaiev.banksystemapi.repository.BankCardRepository;
import com.polezhaiev.banksystemapi.repository.UserRepository;
import com.polezhaiev.banksystemapi.service.user.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;
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

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BankCardRepository bankCardRepository;

    @Test
    @DisplayName("""
            Register a user,
            should return registered user
            """)
    public void register_ValidUser_ShouldReturnValidUser() {
        User user = new User();
        user.setId(1L);

        BankCard bankCard = new BankCard();
        bankCard.setId(1L);

        UserRegistrationRequestDto requestDto = new UserRegistrationRequestDto();

        UserResponseDto expected = new UserResponseDto();
        expected.setId(1L);

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        Mockito.when(userMapper.toModel(any())).thenReturn(user);
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.when(bankCardRepository.save(any())).thenReturn(bankCard);
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any())).thenReturn(user);
        Mockito.when(userMapper.toResponseDto(any())).thenReturn(expected);

        UserResponseDto actual = userService.register(requestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Register a user,
            should throw UserRegistrationException
            """)
    public void register_ExistedUser_ShouldThrowUserRegistrationException() {
        User user = new User();
        user.setId(1L);

        String expected = "There is already a user with such email";

        Mockito.when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> userService.register(new UserRegistrationRequestDto())
        );

        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Get detailed info of a user,
            should return detailed info about a user
            """)
    public void getDetailedInfo_ValidRequestDto_ShouldReturnUserDetailedInfoResponseDto() {
        UserDetailedInfoRequestDto requestDto = new UserDetailedInfoRequestDto();
        requestDto.setPhoneNumber("11111111111111");

        User user = new User();
        user.setId(1L);

        UserDetailedInfoResponseDto expected = new UserDetailedInfoResponseDto();
        expected.setId(1L);

        Mockito.when(userRepository.findByPhoneNumber(any())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByPhoneNumber(any())).thenReturn(Optional.of(user));
        Mockito.when(userMapper.toDetailedInfoResponseDto(any())).thenReturn(expected);

        UserDetailedInfoResponseDto actual = userService.getDetailedInfo(requestDto);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Get detailed info of a user,
            should throw EntityNotFoundException
            """)
    public void getDetailedInfo_InValidRequestDto_ShouldThrowEntityNotFoundException() {
        UserDetailedInfoRequestDto requestDto = new UserDetailedInfoRequestDto();
        requestDto.setPhoneNumber("11111111111111");

        User user = new User();
        user.setId(1L);
        user.setPhoneNumber(requestDto.getPhoneNumber());

        String expected = "Can't find a user by phone number: " + requestDto.getPhoneNumber();

        Mockito.when(userRepository.findByPhoneNumber(any())).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                RuntimeException.class,
                () -> userService.getDetailedInfo(requestDto)
        );

        String actual = exception.getMessage();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find all users,
            should return empty list
            """)
    public void findAll_ShouldReturnEmptyList() {
        List<UserResponseDto> expected = List.of();

        Mockito.when(userRepository.findAll()).thenReturn(List.of());

        List<UserResponseDto> actual = userService.findAll();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("""
            Find all users,
            should return existed user
            """)
    public void findAll_ShouldReturnExistedUser() {
        User user = new User();
        user.setId(1L);

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);

        List<UserResponseDto> expected = List.of(responseDto);

        Mockito.when(userRepository.findAll()).thenReturn(List.of(user));
        Mockito.when(userMapper.toResponseDto(any())).thenReturn(responseDto);

        List<UserResponseDto> actual = userService.findAll();

        Assertions.assertEquals(expected, actual);
    }
}
