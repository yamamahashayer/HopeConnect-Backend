package com.example.HopeConnect.Controllers; // Defines the package for this class

import com.example.HopeConnect.Models.Entity.User; // Imports the User entity
import com.example.HopeConnect.Services.UserServices; // Imports the user service layer
import org.springframework.beans.factory.annotation.Autowired; // Enables dependency injection
import org.springframework.http.ResponseEntity; // Handles HTTP responses
import org.springframework.web.bind.annotation.*; // Imports REST API annotations

import java.util.List;

@RestController // Marks this class as a REST API controller
@RequestMapping("/users") // Base URL path for all endpoints in this controller
public class UserController {

    @Autowired // Automatically injects the UserServices instance
    private UserServices userService;  //bean

    @GetMapping // Handles GET requests to "/users"
    public List<User> getAllUsers() {
        return userService.getAllUsers(); // Calls service to fetch all users
    }

    @GetMapping("/{id}") // Handles GET requests to "/users/{id}"
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id) // Fetches user by ID
                .map(ResponseEntity::ok) // Returns 200 OK if found
                .orElse(ResponseEntity.notFound().build()); // Returns 404 if not found
    }

    @PostMapping // Handles POST requests to "/users"
    public User createUser(@RequestBody User user) {
        return userService.createUser(user); // Calls service to create a new user
    }

    @DeleteMapping("/{id}") // Handles DELETE requests to "/users/{id}"
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id); // Calls service to delete the user by ID
        return ResponseEntity.noContent().build(); // Returns 204 No Content response
    }
}
