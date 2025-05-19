package com.example.HopeConnect.Controllers;



import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.NotificationService;
import com.example.HopeConnect.Services.UserServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserServices userService;


    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getUserNotifications(
            @PathVariable Long userId,
            @RequestParam(required = false) Boolean isRead
    ) {
        Optional<User> userOpt = userService.getUserById(userId);

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }

        User user = userOpt.get();
        List<Notification> notifications = (isRead == null) ?
                notificationService.getNotificationsByOwner(Optional.of(user)) :
                notificationService.getNotificationsByOwnerAndReadStatus(Optional.of(user), isRead);

        return ResponseEntity.ok(notifications);
    }

    /* @PutMapping("/mark-as-read/{id}")
    public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
        try {
            notificationService.markAsRead(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
*/
  @PutMapping("/mark-as-read/{id}")
  public ResponseEntity<?> markNotificationAsRead(@PathVariable Long id) {
      try {
          notificationService.markAsRead(id);
          return ResponseEntity.ok().build();
      } catch (Exception e) {
          return ResponseEntity.status(404).body("Error: " + e.getMessage());
      }
  }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        Notification saved = notificationService.save(notification);
        return ResponseEntity.ok(saved);
    }


    @PostMapping("/send-email")
    public ResponseEntity<Void> sendTestEmail(@RequestParam String to) {
        notificationService.sendEmailNotification(to, "Test Email", " HopeConnect");
        return ResponseEntity.ok().build();
    }

}


















//razan
/*package com.example.HopeConnect.Controllers;

import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.HopeConnect.Services.UserServices;
import java.util.List;
import java.util.Optional;

/*
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserServices userService;
    @GetMapping("/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable Long userId) {
        Optional<User> user = userService.getUserById(userId);
        return notificationService.getNotificationsByOwner(user);
    }

}*/
