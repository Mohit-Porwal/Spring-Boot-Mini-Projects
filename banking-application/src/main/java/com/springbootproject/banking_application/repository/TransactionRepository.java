package com.springbootproject.banking_application.repository;

import com.springbootproject.banking_application.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIdOrderByTimestampDesc(Long accountId);

    //findByAccountId - This part of the method tells Spring Data JPA to create a query that finds transactions based on accountId

    //OrderByTimestampDesc - This part tells the Spring Data JPA to order the results of the query by timestamps in descending order.
    // The recent most transaction will show first

}
