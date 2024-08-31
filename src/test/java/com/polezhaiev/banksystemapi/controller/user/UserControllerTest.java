package com.polezhaiev.banksystemapi.controller.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserDetailedInfoResponseDto;
import com.polezhaiev.banksystemapi.dto.user.UserRegistrationRequestDto;
import com.polezhaiev.banksystemapi.dto.user.UserResponseDto;
import com.polezhaiev.banksystemapi.service.user.UserService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    private UserService userService;

    @Test
    @DisplayName("""
            Register a user,
            should return registered user
            """)
    public void register_ValidRequestData_ShouldReturnRegisteredUser() throws Exception {
        UserRegistrationRequestDto requestDto = getUserRegistrationRequestDto();

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setEmail(requestDto.getEmail());
        responseDto.setPhoneNumber(requestDto.getPhoneNumber());
        responseDto.setFirstName(requestDto.getFirstName());
        responseDto.setLastName(requestDto.getLastName());

        when(userService.register(any(UserRegistrationRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("""
            Get detailed info of a user,
            should return detailed info of a user
            """)
    public void getDetailedInfo_ValidRequestData_ShouldReturnRegisteredUser() throws Exception {
        UserDetailedInfoRequestDto requestDto = new UserDetailedInfoRequestDto();
        requestDto.setPhoneNumber("1234567890");

        UserRegistrationRequestDto registrationRequestDto = getUserRegistrationRequestDto();
        userService.register(registrationRequestDto);

        UserDetailedInfoResponseDto responseDto = getUserDetailedInfoResponseDto(registrationRequestDto);

        when(userService.getDetailedInfo(any(UserDetailedInfoRequestDto.class)))
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(get("/api/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("""
            Find all users,
            should return all valid users
            """)
    public void findAll_ShouldReturnAllValidUsers() throws Exception {
        UserRegistrationRequestDto requestDto = getUserRegistrationRequestDto();

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(requestDto.getEmail());
        userResponseDto.setPhoneNumber(requestDto.getPhoneNumber());
        userResponseDto.setFirstName(requestDto.getFirstName());
        userResponseDto.setLastName(requestDto.getLastName());

        List<UserResponseDto> responseDto = List.of(userResponseDto);

        when(userService.findAll())
                .thenReturn(responseDto);

        String jsonRequest = objectMapper.writeValueAsString(requestDto);
        String expectedResponse = objectMapper.writeValueAsString(responseDto);

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponse));
    }

    private UserDetailedInfoResponseDto getUserDetailedInfoResponseDto(
            UserRegistrationRequestDto registrationRequestDto) {
        UserDetailedInfoResponseDto responseDto = new UserDetailedInfoResponseDto();
        responseDto.setPhoneNumber(registrationRequestDto.getPhoneNumber());
        responseDto.setEmail(registrationRequestDto.getEmail());
        responseDto.setCardsBalance(Map.of(
                registrationRequestDto.getBankCard(),
                registrationRequestDto.getInitialBalance())
        );
        responseDto.setFirstName(registrationRequestDto.getFirstName());
        responseDto.setLastName(registrationRequestDto.getLastName());
        return responseDto;
    }

    private UserRegistrationRequestDto getUserRegistrationRequestDto() {
        UserRegistrationRequestDto registrationRequestDto = new UserRegistrationRequestDto();
        registrationRequestDto.setPhoneNumber("1234567890");
        registrationRequestDto.setEmail("alice@example.com");
        registrationRequestDto.setPassword("1111");
        registrationRequestDto.setBankCard("1111111111111111");
        registrationRequestDto.setInitialBalance(BigDecimal.valueOf(1000));
        registrationRequestDto.setFirstName("alice");
        registrationRequestDto.setLastName("alison");
        return registrationRequestDto;
    }
}
