/*package com.example.HopeConnect.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmailNotification(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }
}*/
/// /maybe return
package com.example.HopeConnect.Services;



import com.example.HopeConnect.Enumes.NotificationType;
import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.NotificationRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

   /* public void sendEmailNotification(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }*/
    public void sendEmailNotification(String to, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());

        }
    }

    public Notification createNotification(Long userId, NotificationType type, String message) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Notification notification = Notification.builder()
                .recipient(userOpt.get())
                .type(type)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        return notificationRepository.save(notification);
    }

   /* public List<Notification> getNotificationsByUserId(Long userId, Boolean unreadOnly) {
        if (unreadOnly != null && unreadOnly) {
            return notificationRepository.findByRecipientIdAndIsReadFalse(userId);
        } else {
            return notificationRepository.findByRecipientId(userId);
        }
    }*/
    public List<Notification> getNotificationsByOwner(Optional<User> userOpt) {
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");
        return notificationRepository.findByRecipientId(userOpt.get().getId());
    }

    public List<Notification> getNotificationsByOwnerAndReadStatus(Optional<User> userOpt, boolean isRead) {
        if (userOpt.isEmpty()) throw new RuntimeException("User not found");

        if (isRead) {
            return notificationRepository.findByRecipientIdAndIsReadTrue(userOpt.get().getId());
        } else {
            return notificationRepository.findByRecipientIdAndIsReadFalse(userOpt.get().getId());
        }
    }


    public void markAsRead(Long notificationId) {
        Notification notif = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notif.setIsRead(true);
        notificationRepository.save(notif);
    }
    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
    }
    public Notification save(Notification notification) {
        if (notification.getRecipient() == null || notification.getRecipient().getId() == null) {
            throw new RuntimeException("Recipient user must be specified");
        }

        notification.setIsRead(false);
        notification.setCreatedAt(LocalDateTime.now());
        return notificationRepository.save(notification);
    }

}









/*
import com.example.HopeConnect.Enumes.NotificationType;
import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.NotificationRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendEmailNotification(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public List<Notification> getNotifications(Long userId, boolean unreadOnly) {
        if (unreadOnly) {
            return notificationRepository.findByRecipientIdAndIsReadFalse(userId);
        } else {
            return notificationRepository.findByRecipientId(userId);
        }
    }

    /* COOOMMMMEEEEEEEENNNNNNTTTTTT  public Notification createNotification(Long userId, NotificationType type, String message) {
           Optional<User> userOpt = userRepository.findById(userId);
           if (userOpt.isEmpty()) {
               throw new RuntimeException("User not found");
           }

           Notification notification = Notification.builder()
                   .recipient(userOpt.get())
                   .type(type)
                   .message(message)
                   .isRead(false)
                   .createdAt(LocalDateTime.now())
                   .build();

           return notificationRepository.save(notification);
       }   HHHHHHHHHHEEEEEEERRRRRRREEEEEEEEE
    public Notification createNotification(Long userId, NotificationType type, String message) {
        System.out.println(">> Inside createNotification for user: " + userId + ", type: " + type);

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            System.out.println("!! User not found for notification");
            throw new RuntimeException("User not found");
        }

        Notification notification = Notification.builder()
                .recipient(userOpt.get())
                .type(type)
                .message(message)
                .isRead(false)
                .createdAt(LocalDateTime.now())
                .build();

        Notification savedNotif = notificationRepository.save(notification);
        System.out.println(">> Notification saved with ID: " + savedNotif.getId());
        return savedNotif;
    }


    public void markAsRead(Long notificationId) {
        Optional<Notification> notifOpt = notificationRepository.findById(notificationId);
        if (notifOpt.isPresent()) {
            Notification notif = notifOpt.get();
            notif.setIsRead(true);
            notificationRepository.save(notif);
        } else {
            throw new RuntimeException("Notification not found");
        }
    }
}
*/
