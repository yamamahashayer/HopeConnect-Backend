package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Notification;
import com.example.HopeConnect.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
   // List<Notification> findAllByOwner(User owner);
   // List<Notification> findAllByOwnerAndIsRead(User owner, boolean isRead);
}
*/


@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByRecipientIdAndIsReadFalse(Long recipientId);
    List<Notification> findByRecipientId(Long recipientId);
    List<Notification> findByRecipientIdAndIsReadTrue(Long recipientId);

}
