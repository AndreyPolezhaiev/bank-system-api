package com.polezhaiev.banksystemapi.repository;

import com.polezhaiev.banksystemapi.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    protected UserRepository userRepository;

    @Test
    @DisplayName("""
            Find a user by phone number,
            should return valid user
            """)
    @Sql(scripts = {
            "classpath:database/user/01-add-one-user-to-database.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Rollback
    public void findByPhoneNumber_ValidPhoneNumber_ShouldReturnValidUser() {
        String phoneNumber = "111111111111";

        User expected = new User();
        expected.setPhoneNumber(phoneNumber);
        expected.setEmail("alice@gmail.com");
        expected.setLastName("alison");
        expected.setFirstName("alice");
        expected.setPassword("1111");

        User actual = userRepository.findByPhoneNumber(phoneNumber).get();

        Assertions.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    }

    @Test
    @DisplayName("""
            Find a user by email,
            should return valid user
            """)
    @Sql(scripts = {
            "classpath:database/user/01-add-one-user-to-database.sql"
    },
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Rollback
    public void findByEmail_ValidEmail_ShouldReturnValidUser() {
        String email = "alice@gmail.com";

        User expected = new User();
        expected.setPhoneNumber("111111111111");
        expected.setEmail(email);
        expected.setLastName("alison");
        expected.setFirstName("alice");
        expected.setPassword("1111");

        User actual = userRepository.findByEmail(email).get();

        Assertions.assertEquals(expected.getPhoneNumber(), actual.getPhoneNumber());
        Assertions.assertEquals(expected.getEmail(), actual.getEmail());
    }
}
