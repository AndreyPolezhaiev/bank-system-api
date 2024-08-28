package com.polezhaiev.banksystemapi.service.user;

import com.polezhaiev.banksystemapi.dto.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.UserResponseDto;
import com.polezhaiev.banksystemapi.exception.UserRegistrationException;
import java.util.List;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserDetailedInfoResponseDto getDetailedInfo(UserDetailedInfoRequestDto requestDto);

    List<UserResponseDto> findAll();
}
