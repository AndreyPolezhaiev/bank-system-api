package com.polezhaiev.banksystemapi.repository;

import com.polezhaiev.banksystemapi.model.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankCardRepository extends JpaRepository<BankCard, Long> {
}
