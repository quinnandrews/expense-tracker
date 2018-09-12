package com.quinnandrews.rest.webservices.expensetracker.repository;

import com.quinnandrews.rest.webservices.expensetracker.entity.Measure;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * <p>Repository Interface to manage instances of Measure.
 *
 * @author Quinn Andrews
 *
 */
public interface MeasureRepository extends ExpenseTrackerRepository<Measure> {

    @Query(value = "select (count(*) > 0) from transaction_item where measure_id = :measureId", nativeQuery = true)
    Boolean hasTransactionItems(@Param("measureId")  Long measureId);

}
