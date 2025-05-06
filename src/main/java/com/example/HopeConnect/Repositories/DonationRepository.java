package com.example.HopeConnect.Repositories;

import com.example.HopeConnect.Models.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {

    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM Donation d")
    Double sumTotalAmount();
}
