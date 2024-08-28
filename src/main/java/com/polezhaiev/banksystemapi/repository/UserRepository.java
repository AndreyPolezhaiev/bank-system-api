package com.polezhaiev.banksystemapi.repository;

import com.polezhaiev.banksystemapi.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmail(String email);
}
