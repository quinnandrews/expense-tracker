package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of Transaction.
 *
 * @author Quinn Andrews
 *
 */
public interface TransactionRepository extends ExpenseTrackerRepository<Transaction> {

    @Query(value = "select (count(*) > 0) from transaction_item where transaction_id = :transactionId", nativeQuery = true)
    Boolean hasTransactionItems(@Param("transactionId")  Long itemId);

}
