package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.Merchant;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.MerchantRepository;
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
 * <p>Controller for handling resource requests in regard to instances of Merchant.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class MerchantController extends ExpenseTrackerController {

    @Autowired
    private MerchantRepository merchantRepository;

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/merchants")
    @ApiOperation("Gets a List of all Merchants.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Merchants were found without issue.")
    })
    public List<Merchant> getMerchants() {
        return merchantRepository.findAll(Sort.by("name"));
    }

    @GetMapping(path = "/merchants/{merchantId}")
    @ApiOperation("Gets the Merchant with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Merchant was found without issue."),
            @ApiResponse(code = 404, message = "A Merchant with the specified ID does not exist.")
    })
    public Merchant getMerchant(@PathVariable Long merchantId) {
        return findMerchant(merchantId);
    }

    @PostMapping("/merchants")
    @ApiOperation("Creates a new Merchant.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Merchant was created without issue."),
            @ApiResponse(code = 400, message = "The Merchant could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<Merchant> createMerchant(@Valid @RequestBody Merchant merchant) {
        merchantRepository.save(merchant);
        return ResponseEntity.created(generateURI(merchant.getId())).build();
    }

    @PutMapping("/merchants/{merchantId}")
    @ApiOperation("Updates an existing Merchant.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Merchant was updated without issue."),
            @ApiResponse(code = 400, message = "The Merchant could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "A Merchant with the specified ID does not exist.")
    })
    public ResponseEntity<Merchant> updateMerchant(@Valid @RequestBody Merchant merchant) {
        if (!merchantRepository.existsById(merchant.getId())) {
            throw new EntityNotFoundException(Merchant.class, merchant.getId());
        }
        merchantRepository.save(merchant);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/merchants/{merchantId}")
    @ApiOperation("Deletes an existing Merchant.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Merchant was deleted without issue."),
            @ApiResponse(code = 403, message = "The Merchant could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "A Merchant with the specified ID does not exist.")
    })
    public ResponseEntity<Merchant> deleteMerchant(@PathVariable Long merchantId) {
        if (merchantRepository.hasTransactions(merchantId)) {
            throw new EntityCannotBeDeletedException(Merchant.class, merchantId);
        }
        merchantRepository.delete(findMerchant(merchantId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private Merchant findMerchant(Long merchantId) {
        Optional<Merchant> merchant = merchantRepository.findById(merchantId);
        if (!merchant.isPresent()) {
            throw new EntityNotFoundException(Merchant.class, merchantId);
        }
        return merchant.get();
    }

}
