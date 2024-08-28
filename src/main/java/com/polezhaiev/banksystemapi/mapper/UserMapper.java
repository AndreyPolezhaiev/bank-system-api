package com.polezhaiev.banksystemapi.mapper;

import com.polezhaiev.banksystemapi.config.MapperConfig;
import com.polezhaiev.banksystemapi.dto.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.UserResponseDto;
import com.polezhaiev.banksystemapi.model.BankCard;
import com.polezhaiev.banksystemapi.model.User;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface UserMapper {
    User toModel(UserRegistrationRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
    @Mapping(target = "bankCards", ignore = true)
    UserDetailedInfoResponseDto toDetailedInfoResponseDto(User user);

    @AfterMapping
    default void setBankCards(@MappingTarget UserDetailedInfoResponseDto responseDto, User user) {
        Map<String, BigDecimal> cardsBalance = new HashMap<>();
        for (BankCard bankCard: user.getBankCards()) {
            cardsBalance.put(bankCard.getCardNumber(), bankCard.getBalance());
        }
        responseDto.setCardsBalance(cardsBalance);
    }
}
