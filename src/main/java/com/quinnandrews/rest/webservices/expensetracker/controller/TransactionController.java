package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.Transaction;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.TransactionRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * <p>Controller for handling resource requests in regard to instances of Transaction.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class TransactionController extends ExpenseTrackerController {

    @Autowired
    private TransactionRepository transactionRepository;

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/transactions")
    @ApiOperation("Gets a List of all Transactions.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Transactions were found without issue.")
    })
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll(Sort.by("date"));
    }

    @GetMapping(path = "/transactions/{transactionId}")
    @ApiOperation("Gets the Transaction with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Transaction was found without issue."),
            @ApiResponse(code = 404, message = "A Transaction with the specified ID does not exist.")
    })
    public Transaction getTransaction(@PathVariable Long transactionId) {
        return findTransaction(transactionId);
    }

    @PostMapping("/transactions")
    @ApiOperation("Creates a new Transaction.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Transaction was created without issue."),
            @ApiResponse(code = 400, message = "The Transaction could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction) {
        transactionRepository.save(transaction);
        return ResponseEntity.created(generateURI(transaction.getId())).build();
    }

    @PutMapping("/transactions/{transactionId}")
    @ApiOperation("Updates an existing Transaction.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Transaction was updated without issue."),
            @ApiResponse(code = 400, message = "The Transaction could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "A Transaction with the specified ID does not exist.")
    })
    public ResponseEntity<Transaction> updateTransaction(@Valid @RequestBody Transaction transaction) {
        if (!transactionRepository.existsById(transaction.getId())) {
            throw new EntityNotFoundException(Transaction.class, transaction.getId());
        }
        transactionRepository.save(transaction);
        return ResponseEntity.ok().build();
    }

    // Deleting should cascade to TransactionItems
    @DeleteMapping("/transactions/{transactionId}")
    @ApiOperation("Deletes an existing Transaction.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Transaction was deleted without issue."),
            @ApiResponse(code = 403, message = "The Transaction could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "A Transaction with the specified ID does not exist.")
    })
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long transactionId) {
        if (transactionRepository.hasTransactionItems(transactionId)) {
            throw new EntityCannotBeDeletedException(Transaction.class, transactionId);
        }
        transactionRepository.delete(findTransaction(transactionId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private Transaction findTransaction(Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (!transaction.isPresent()) {
            throw new EntityNotFoundException(Transaction.class, transactionId);
        }
        return transaction.get();
    }

}
