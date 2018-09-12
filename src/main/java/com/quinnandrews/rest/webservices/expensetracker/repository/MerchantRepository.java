package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.Merchant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of Merchant.
 *
 * @author Quinn Andrews
 *
 */
public interface MerchantRepository extends ExpenseTrackerRepository<Merchant> {

    @Query(value = "select (count(*) > 0) from transaction where merchant_id = :merchantId", nativeQuery = true)
    Boolean hasTransactions(@Param("merchantId")  Long merchantId);

}
