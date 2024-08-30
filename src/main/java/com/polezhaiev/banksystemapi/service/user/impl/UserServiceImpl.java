package com.polezhaiev.banksystemapi.service.user.impl;

import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserResponseDto;
import com.polezhaiev.banksystemapi.exception.EntityNotFoundException;
import com.polezhaiev.banksystemapi.exception.UserRegistrationException;
import com.polezhaiev.banksystemapi.mapper.UserMapper;
import com.polezhaiev.banksystemapi.model.BankCard;
import com.polezhaiev.banksystemapi.model.User;
import com.polezhaiev.banksystemapi.repository.BankCardRepository;
import com.polezhaiev.banksystemapi.repository.UserRepository;
import com.polezhaiev.banksystemapi.service.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final BankCardRepository bankCardRepository;

    @Transactional
    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new UserRegistrationException("There is already a user with such email");
        }
        User user = userMapper.toModel(requestDto);
        user.setBankCards(Set.of());
        User saved = userRepository.save(user);

        BankCard bankCard = new BankCard();
        bankCard.setUser(user);
        bankCard.setBalance(requestDto.getInitialBalance());
        bankCard.setCardNumber(requestDto.getBankCard());
        BankCard savedBankCard = bankCardRepository.save(bankCard);

        User fromRepo = userRepository.findById(saved.getId()).get();
        Set<BankCard> mutableBankCards = new HashSet<>(fromRepo.getBankCards());
        mutableBankCards.add(savedBankCard);
        fromRepo.setBankCards(mutableBankCards);
        User savedUser = userRepository.save(fromRepo);

        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public UserDetailedInfoResponseDto getDetailedInfo(UserDetailedInfoRequestDto requestDto) {
        if (userRepository.findByPhoneNumber(requestDto.getPhoneNumber()).isEmpty()) {
            throw new EntityNotFoundException("Can't find a user by phone number: " + requestDto.getPhoneNumber());
        }
        User user = userRepository.findByPhoneNumber(requestDto.getPhoneNumber()).get();
        return userMapper.toDetailedInfoResponseDto(user);
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }
}
