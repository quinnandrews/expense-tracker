package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.Measure;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.MeasureRepository;
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
 * <p>Controller for handling resource requests in regard to instances of Measure.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class MeasureController extends ExpenseTrackerController {

    @Autowired
    private MeasureRepository measureRepository;

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/measures")
    @ApiOperation("Gets a List of all Measures.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Measures were found without issue.")
    })
    public List<Measure> getMeasures() {
        return measureRepository.findAll(Sort.by("name"));
    }

    @GetMapping(path = "/measures/{measureId}")
    @ApiOperation("Gets the Measure with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Measure was found without issue."),
            @ApiResponse(code = 404, message = "A Measure with the specified ID does not exist.")
    })
    public Measure getMeasure(@PathVariable Long measureId) {
        return findMeasure(measureId);
    }

    @PostMapping("/measures")
    @ApiOperation("Creates a new Measure.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The Measure was created without issue."),
            @ApiResponse(code = 400, message = "The Measure could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<Measure> createMeasure(@Valid @RequestBody Measure measure) {
        measureRepository.save(measure);
        return ResponseEntity.created(generateURI(measure.getId())).build();
    }

    @PutMapping("/measures/{measureId}")
    @ApiOperation("Updates an existing Measure.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Measure was updated without issue."),
            @ApiResponse(code = 400, message = "The Measure could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "A Measure with the specified ID does not exist.")
    })
    public ResponseEntity<Measure> updateMeasure(@Valid @RequestBody Measure measure) {
        if (!measureRepository.existsById(measure.getId())) {
            throw new EntityNotFoundException(Measure.class, measure.getId());   
        }
        measureRepository.save(measure);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/measures/{measureId}")
    @ApiOperation("Deletes an existing Measure.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The Measure was deleted without issue."),
            @ApiResponse(code = 403, message = "The Measure could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "A Measure with the specified ID does not exist.")
    })
    public ResponseEntity<Measure> deleteMeasure(@PathVariable Long measureId) {
        if (measureRepository.hasTransactionItems(measureId)) {
            throw new EntityCannotBeDeletedException(Measure.class, measureId);
        }
        measureRepository.delete(findMeasure(measureId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private Measure findMeasure(Long measureId) {
        Optional<Measure> measure = measureRepository.findById(measureId);
        if (!measure.isPresent()) {
            throw new EntityNotFoundException(Measure.class, measureId);
        }
        return measure.get();
    }

}
