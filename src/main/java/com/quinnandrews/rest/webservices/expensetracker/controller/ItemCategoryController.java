package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.ItemCategory;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.ItemCategoryRepository;
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
 * <p>Controller for handling resource requests in regard to instances of ItemCategory.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class ItemCategoryController extends ExpenseTrackerController {

    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    
    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/itemCategories")
    @ApiOperation("Gets a List of all Item Categories.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Item Categories were found without issue.")
    })
    public List<ItemCategory> getItemCategorys() {
        return itemCategoryRepository.findAll(Sort.by("name"));
    }

    @GetMapping(path = "/itemCategories/{itemCategoryId}")
    @ApiOperation("Gets the Item Category with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Item Category was found without issue."),
            @ApiResponse(code = 404, message = "An Item Category with the specified ID does not exist.")
    })
    public ItemCategory getItemCategory(@PathVariable Long itemCategoryId) {
        return findItemCategory(itemCategoryId);
    }

    @PostMapping("/itemCategories")
    @ApiOperation("Creates a new Item Category.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Item Category was created without issue."),
            @ApiResponse(code = 400, message = "The Item Category could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<ItemCategory> createItemCategory(@Valid @RequestBody ItemCategory itemCategory) {
        itemCategoryRepository.save(itemCategory);
        return ResponseEntity.created(generateURI(itemCategory.getId())).build();
    }

    @PutMapping("/itemCategories/{itemCategoryId}")
    @ApiOperation("Updates an existing Item Category.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Item Category was updated without issue."),
            @ApiResponse(code = 400, message = "The Item Category could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "An Item Category with the specified ID does not exist.")
    })
    public ResponseEntity<ItemCategory> updateItemCategory(@Valid @RequestBody ItemCategory itemCategory) {
        if (!itemCategoryRepository.existsById(itemCategory.getId())) {
            throw new EntityNotFoundException(ItemCategory.class, itemCategory.getId());
        }
        itemCategoryRepository.save(itemCategory);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/itemCategories/{itemCategoryId}")
    @ApiOperation("Deletes an existing Item Category.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Item Category was deleted without issue."),
            @ApiResponse(code = 403, message = "The Item Category could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "An Item Category with the specified ID does not exist.")
    })
    public ResponseEntity<ItemCategory> deleteItemCategory(@PathVariable Long itemCategoryId) {
        if (itemCategoryRepository.hasItems(itemCategoryId)) {
            throw new EntityCannotBeDeletedException(ItemCategory.class, itemCategoryId);
        }
        itemCategoryRepository.delete(findItemCategory(itemCategoryId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private ItemCategory findItemCategory(Long itemCategoryId) {
        Optional<ItemCategory> itemCategory = itemCategoryRepository.findById(itemCategoryId);
        if (!itemCategory.isPresent()) {
            throw new EntityNotFoundException(ItemCategory.class, itemCategoryId);
        }
        return itemCategory.get();
    }

}
