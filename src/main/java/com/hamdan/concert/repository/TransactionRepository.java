package com.hamdan.concert.repository;

import com.hamdan.concert.entity.Customer;
import com.hamdan.concert.entity.Transaction;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @QueryHints({@QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000")})
    List<Transaction> findTransactionsByConcertCodeAndCustomer(String codeConcert, Customer customer);
}
