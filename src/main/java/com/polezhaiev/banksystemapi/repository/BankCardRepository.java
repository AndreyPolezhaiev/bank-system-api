package com.polezhaiev.banksystemapi.repository;

import com.polezhaiev.banksystemapi.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankCardRepository extends JpaRepository<BankCard, Long> {
}
