package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of User.
 *
 * @author Quinn Andrews
 *
 */
public interface UserRepository extends ExpenseTrackerRepository<User> {

    @Query(value = "select (count(*) > 0) from transaction where user_id = :userId", nativeQuery = true)
    Boolean hasTransactions(@Param("userId")  Long userId);

}
