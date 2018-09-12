package com.quinnandrews.rest.webservices.expensetracker.controller;

import com.quinnandrews.rest.webservices.expensetracker.entity.User;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityCannotBeDeletedException;
import com.quinnandrews.rest.webservices.expensetracker.exception.EntityNotFoundException;
import com.quinnandrews.rest.webservices.expensetracker.repository.UserRepository;
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
 * <p>Controller for handling resource requests in regard to instances of User.
 *
 * @author Quinn Andrews
 *
 */
@RestController
public class UserController extends ExpenseTrackerController {

    @Autowired
    private UserRepository userRepository;

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> REST METHODS */

    @GetMapping(path = "/users")
    @ApiOperation("Gets a List of all Users.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The Users were found without issue.")
    })
    public List<User> getUsers() {
        return userRepository.findAll(Sort.by("name"));
    }

    @GetMapping(path = "/users/{userId}")
    @ApiOperation("Gets the User with the specified ID.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The User was found without issue."),
            @ApiResponse(code = 404, message = "A User with the specified ID does not exist.")
    })
    public User getUser(@PathVariable Long userId) {
        return findUser(userId);
    }

    @PostMapping("/users")
    @ApiOperation("Creates a new User.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The User was created without issue."),
            @ApiResponse(code = 400, message = "The User could not be created because one or more validation errors occurred.")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        return ResponseEntity.created(generateURI(user.getId())).build();
    }

    @PutMapping("/users/{userId}")
    @ApiOperation("Updates an existing User.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The User was updated without issue."),
            @ApiResponse(code = 400, message = "The User could not be updated because one or more validation errors occurred."),
            @ApiResponse(code = 404, message = "A User with the specified ID does not exist.")
    })
    public ResponseEntity<User> updateUser(@Valid @RequestBody User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException(User.class, user.getId());
        }
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/users/{userId}")
    @ApiOperation("Deletes an existing User.")
    @ApiResponses({
            @ApiResponse(code = 204, message = "The User was deleted without issue."),
            @ApiResponse(code = 403, message = "The User could not be deleted due to foreign key constraints."),
            @ApiResponse(code = 404, message = "A User with the specified ID does not exist.")
    })
    public ResponseEntity<User> deleteUser(@PathVariable Long userId) {
        if (userRepository.hasTransactions(userId)) {
            throw new EntityCannotBeDeletedException(User.class, userId);
        }
        userRepository.delete(findUser(userId));
        return ResponseEntity.noContent().build();
    }

    /* >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> PRIVATE METHODS */

    private User findUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new EntityNotFoundException(User.class, userId);
        }
        return user.get();
    }

}
