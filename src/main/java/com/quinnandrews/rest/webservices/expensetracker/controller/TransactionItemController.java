package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.TransactionItem;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.TransactionItemRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * <p>Controller for handling resource requests in regard to instances of TransactionItem.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class TransactionItemController extends ExpenseTrackerController {

    @Autowired
    private TransactionItemRepository transactionItemRepository;

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/transactionItems")
    @ApiOperation("Gets a List of all Transaction Item.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Users were found without issue.")
    })
    public List<TransactionItem> getTransactionItems() {
        return transactionItemRepository.findAll();
    }

    @GetMapping(path = "/transactionItems/{transactionItemId}")
    @ApiOperation("Gets the Transaction Item with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Transaction Item was found without issue."),
            @ApiResponse(code = 404, message = "A Transaction Item with the specified ID does not exist.")
    })
    public TransactionItem getTransactionItem(@PathVariable Long transactionItemId) {
        return findTransactionItem(transactionItemId);
    }

    @PostMapping("/transactionItems")
    @ApiOperation("Creates a new Transaction Item.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Transaction Item was created without issue."),
            @ApiResponse(code = 400, message = "The Transaction Item could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<TransactionItem> createTransactionItem(@Valid @RequestBody TransactionItem transactionItem) {
        transactionItemRepository.save(transactionItem);
        return ResponseEntity.created(generateURI(transactionItem.getId())).build();
    }

    @PutMapping("/transactionItems/{transactionItemId}")
    @ApiOperation("Updates an existing Transaction Item.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Transaction Item was updated without issue."),
            @ApiResponse(code = 400, message = "The Transaction Item could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "A Transaction Item with the specified ID does not exist.")
    })
    public ResponseEntity<TransactionItem> updateTransactionItem(@Valid @RequestBody TransactionItem transactionItem) {
        if (!transactionItemRepository.existsById(transactionItem.getId())) {
            throw new EntityNotFoundException(TransactionItem.class, transactionItem.getId());
        }
        transactionItemRepository.save(transactionItem);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/transactionItems/{transactionItemId}")
    @ApiOperation("Deletes an existing Transaction Item.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Transaction Item was deleted without issue."),
            @ApiResponse(code = 404, message = "A Transaction Item with the specified ID does not exist.")
    })
    public ResponseEntity<TransactionItem> deleteTransactionItem(@PathVariable Long transactionItemId) {
        transactionItemRepository.delete(findTransactionItem(transactionItemId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private TransactionItem findTransactionItem(Long transactionItemId) {
        Optional<TransactionItem> transactionItem = transactionItemRepository.findById(transactionItemId);
        if (!transactionItem.isPresent()) {
            throw new EntityNotFoundException(TransactionItem.class, transactionItemId);
        }
        return transactionItem.get();
    }

}
