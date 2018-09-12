package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.Item;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.ItemRepository;
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
 * <p>Controller for handling resource requests in regard to instances of Item.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class ItemController extends ExpenseTrackerController {

    @Autowired
    private ItemRepository itemRepository;
    
    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/items")
    @ApiOperation("Gets a List of all Items.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Items were found without issue.")
    })
    public List<Item> getItems() {
        return itemRepository.findAll(Sort.by("name"));
    }

    @GetMapping(path = "/items/{itemId}")
    @ApiOperation("Gets the Item with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Item was found without issue."),
            @ApiResponse(code = 404, message = "An Item with the specified ID does not exist.")
    })
    public Item getItem(@PathVariable Long itemId) {
        return findItem(itemId);
    }

    @PostMapping("/items")
    @ApiOperation("Creates a new Item.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Item was created without issue."),
            @ApiResponse(code = 400, message = "The Item could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        itemRepository.save(item);
        return ResponseEntity.created(generateURI(item.getId())).build();
    }

    @PutMapping("/items/{itemId}")
    @ApiOperation("Updates an existing Item.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Item was updated without issue."),
            @ApiResponse(code = 400, message = "The Item could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "An Item with the specified ID does not exist.")
    })
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item) {
        if (!itemRepository.existsById(item.getId())) {
            throw new EntityNotFoundException(Item.class, item.getId());
        }
        itemRepository.save(item);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/items/{itemId}")
    @ApiOperation("Deletes an existing Item.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Item was deleted without issue."),
            @ApiResponse(code = 403, message = "The Item could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "An Item with the specified ID does not exist.")
    })
    public ResponseEntity<Item> deleteItem(@PathVariable Long itemId) {
        if (itemRepository.hasTransactionItems(itemId)) {
            throw new EntityCannotBeDeletedException(Item.class, itemId);
        }
        itemRepository.delete(findItem(itemId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private Item findItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (!item.isPresent()) {
            throw new EntityNotFoundException(Item.class, itemId);
        }
        return item.get();
    }

}
