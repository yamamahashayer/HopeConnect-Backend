package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Config.JWTService;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private UserServices userService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JWTService jwtService;



    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody User user) {
        try {
            // تحقق إذا الإيميل مستخدم سابقًا
            Optional<User> existingUser = userService.findByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(Map.of("error", "Email already exists"));
            }

            // تشفير كلمة المرور
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // إنشاء مستخدم جديد
            User createdUser = userService.createUser(user);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Signup successful");
            response.put("user", createdUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Signup failed: " + e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }

        if (userService.checkPassword(loginRequest.getPassword(), user.get().getPassword())) {
            // ✅ توليد التوكن باستخدام JWTService
            String token = jwtService.generateToken(user.get().getEmail(), user.get().getUserType().toString());

            // ✅ إرجاع التوكن ضمن الاستجابة
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("userType", user.get().getUserType().toString());
            response.put("userName", user.get().getName());
            response.put("userId", user.get().getId());
            response.put("email", user.get().getEmail());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
    }

}
