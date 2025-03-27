package com.example.HopeConnect.Conrollers;

import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServices userService;

    // للحصول على جميع المستخدمين
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build(); // إذا لم تكن هناك بيانات
        }
        return ResponseEntity.ok(users); // إرجاع جميع المستخدمين
    }

    // للحصول على مستخدم واحد بناءً على الـ id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get()); // إرجاع المستخدم إذا تم العثور عليه
        } else {
            return ResponseEntity.notFound().build(); // إرجاع خطأ 404 إذا لم يتم العثور على المستخدم
        }
    }

    // لإضافة مستخدم جديد
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            return ResponseEntity.status(201).body(createdUser); // إرجاع المستخدم الذي تم إنشاؤه مع حالة 201
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage()); // إرجاع رسالة الخطأ
        }
    }

    // لحذف مستخدم بناءً على الـ id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    // لتحديث مستخدم بناءً على الـ id
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // **إضافة Endpoint الـ login**
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        // تحقق من البريد الإلكتروني وكلمة المرور
        Optional<User> user = userService.getUserByEmailAndPassword(email, password);
        if (user.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login successful", "user", user.get()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid email or password"));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return userService.signUp(user);
    }


}
