package com.example.HopeConnect.Controllers;
/*
import com.example.HopeConnect.Enumes.NotificationType;
import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Services.NotificationService;
import com.example.HopeConnect.Services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(

            @PathVariable Long userId,
            @RequestParam(defaultValue = "false") boolean unreadOnly) {
        List<Notification> notifs = notificationService.getNotifications(userId, unreadOnly);
        return ResponseEntity.ok(notifs);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Notification> createNotification(
            @PathVariable Long userId,
            @RequestParam NotificationType type,
            @RequestParam String message) {
        Notification notif = notificationService.createNotification(userId, type, message);
        return ResponseEntity.ok(notif);
    }

    @PutMapping("/mark-as-read/{notificationId}")
    public ResponseEntity<Void> markAsRead(@PathVariable Long notificationId) {
        notificationService.markAsRead(notificationId);
        return ResponseEntity.ok().build();
    }
}*/
/// /////////////////////////////


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
        try {
            Optional<User> user = userService.getUserById(userId);
            List<Notification> notifications = (isRead == null) ?
                    notificationService.getNotificationsByOwner(user) :
                    notificationService.getNotificationsByOwnerAndReadStatus(user, isRead);

            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
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
