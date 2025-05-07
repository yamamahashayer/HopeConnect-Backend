package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByStripeSessionId(String sessionId); // إضافة دالة البحث
}
