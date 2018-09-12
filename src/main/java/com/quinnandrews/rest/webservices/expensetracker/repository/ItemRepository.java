package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of Item.
 *
 * @author Quinn Andrews
 *
 */
public interface ItemRepository extends ExpenseTrackerRepository<Item> {

    @Query(value = "select (count(*) > 0) from transaction_item where item_id = :itemId", nativeQuery = true)
    Boolean hasTransactionItems(@Param("itemId")  Long itemId);

}
