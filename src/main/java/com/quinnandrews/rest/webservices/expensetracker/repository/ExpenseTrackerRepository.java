package com.quinnandrews.rest.webservices.expensetracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * <p>Common Interface for Repositories. Allows for convenient implementation of both JpaRespository and
 * JpaSpecificationExecutor.
 *
 * @author Quinn Andrews
 *
 */
@NoRepositoryBean
public interface ExpenseTrackerRepository<T> extends JpaRepository<T, Long>, JpaSpecificationExecutor<T> {

}
