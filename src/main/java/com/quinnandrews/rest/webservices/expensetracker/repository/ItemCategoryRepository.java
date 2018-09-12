package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.ItemCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of ItemCategory.
 *
 * @author Quinn Andrews
 *
 */
public interface ItemCategoryRepository extends ExpenseTrackerRepository<ItemCategory> {

    @Query(value = "select (count(*) > 0) from item where item_category_id = :itemCategoryId", nativeQuery = true)
    Boolean hasItems(@Param("itemCategoryId")  Long itemCategoryId);

}
