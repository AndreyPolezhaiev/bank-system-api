package com.polezhaiev.banksystemapi.service.user;

import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    UserDetailedInfoResponseDto getDetailedInfo(UserDetailedInfoRequestDto requestDto);

    List<UserResponseDto> findAll();
}
