package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.SponsorActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SponsorActivityRepository extends JpaRepository<SponsorActivity, Long> {
    // دالة للبحث عن الأنشطة بناءً على الـ sponsorId
    List<SponsorActivity> findBySponsorId(Long sponsorId);
}
