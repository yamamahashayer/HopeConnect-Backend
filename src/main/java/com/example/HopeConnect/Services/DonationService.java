package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElse(null);
    }

    public Donation createDonation(Donation donation) {
        return donationRepository.save(donation);
    }

 /* public Donation updateDonation(Long id, Donation updatedDonation) {
        return donationRepository.findById(id).map(donation -> {
            donation.setAmount(updatedDonation.getAmount());
            donation.setCurrency(updatedDonation.getCurrency());
            donation.setDonationType(updatedDonation.getDonationType());
            donation.setPaymentStatus(updatedDonation.getPaymentStatus());
            return donationRepository.save(donation);
        }).orElse(null);
    }

////////////////////////////////////////////////
    public Donation updateDonation(Long id, Donation updatedDonation) {
        Optional<Donation> existingDonationOpt = donationRepository.findById(id);

        if (existingDonationOpt.isPresent()) {
            Donation existingDonation = existingDonationOpt.get();

            // Update donation details
            existingDonation.setAmount(updatedDonation.getAmount());
            existingDonation.setCurrency(updatedDonation.getCurrency());
            existingDonation.setDonationType(updatedDonation.getDonationType());
            existingDonation.setPaymentStatus(updatedDonation.getPaymentStatus());
            existingDonation.setDonationDate(updatedDonation.getDonationDate());

            // Save and return updated donation
            return donationRepository.save(existingDonation);
        } else {
            return null;  // Donation not found
        }
    }
*/
 public Donation updateDonation(Long id, Donation updatedDonation) {
     Optional<Donation> existingDonationOpt = donationRepository.findById(id);

     if (existingDonationOpt.isPresent()) {
         Donation existingDonation = existingDonationOpt.get();

         // Ensure fields are updated if provided in the request
         if (updatedDonation.getAmount() != null) {
             existingDonation.setAmount(updatedDonation.getAmount());
         }
         if (updatedDonation.getCurrency() != null) {
             existingDonation.setCurrency(updatedDonation.getCurrency());
         }
         if (updatedDonation.getDonationType() != null) {
             existingDonation.setDonationType(updatedDonation.getDonationType());
         }
         if (updatedDonation.getPaymentStatus() != null) {
             existingDonation.setPaymentStatus(updatedDonation.getPaymentStatus());
         }
         if (updatedDonation.getDonationDate() != null) {
             existingDonation.setDonationDate(updatedDonation.getDonationDate());
         }

         // Save and return updated donation
         return donationRepository.save(existingDonation);
     } else {
         return null;  // Donation not found
     }
 }

    public boolean deleteDonation(Long id) {
        return donationRepository.findById(id).map(donation -> {
            donationRepository.delete(donation);
            return true;
        }).orElse(false);
    }


}
