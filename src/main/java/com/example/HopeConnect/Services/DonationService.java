package com.example.HopeConnect.Services;

import com.example.HopeConnect.Enumes.NotificationType;
import com.example.HopeConnect.Models.Donation;
import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.DonationRepository;
import com.example.HopeConnect.Repositories.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    @Autowired
    private static DonationRepository donationRepository;

    public List<Donation> getAllDonations() {
        return donationRepository.findAll();
    }

    public static Donation getDonationById(Long id) {
        return donationRepository.findById(id).orElse(null);
    }
    @Autowired
    private   DonorRepository donorRepository;
    private final NotificationService notificationService;
    @Autowired
    public DonationService(DonationRepository donationRepository,
                           DonorRepository donorRepository,
                           NotificationService notificationService) {
        this.donationRepository = donationRepository;
        this.donorRepository = donorRepository;
        this.notificationService = notificationService;
    }
    /*public Donation createDonation(Donation donation) {


        Donation saved = donationRepository.save(donation);


        String email = saved.getDonor().getUser().getEmail();




        notificationService.sendEmailNotification(
                email,
                "Thank you for your donation .",
                "Your donation has been registered " + saved.getAmount() + " " + saved.getCurrency()
        );


        return saved;
        //return donationRepository.save(donation);
    }*/
    /*public Donation createDonation(Donation donation) {
        Donation saved = donationRepository.save(donation);

        Donation -> Donor -> User
        User user = saved.getDonor().getUser();
        String email = user.getEmail();
       // System.out.println(" Sending email to: " + email);


        try{notificationService.sendEmailNotification(

                email,
                "Thank you for your donation.",
                "Dear " + user.getName() + "\n\n" +
                        "Thank you for your generous donation. Your donation has been received. " + saved.getAmount() + " " + saved.getCurrency()
        );}
        catch(Exception e){

            System.err.println("Failed to send email to " + email + ": " + e.getMessage());

        }


        notificationService.createNotification(
                user.getId(),
                NotificationType.DONATION,
                "Your donation has been received. " + saved.getAmount() + " " + saved.getCurrency() + ".Thank you for your contribution!"
        );

        return saved;
    }
    */

    public Donation createDonation(Donation donation) {

        Donation saved = donationRepository.save(donation);

        Donor donor = donorRepository.findByUserId(saved.getDonor().getUser().getId())
                .orElseThrow(() -> new RuntimeException("Donor not found by userId"));

        User user = donor.getUser();


        notificationService.createNotification(
                user.getId(),
                NotificationType.DONATION,
                "Your donation has been received. " + saved.getAmount() + " " + saved.getCurrency() + ". Thank you for your contribution!"
        );

        return saved;
    }
/**/

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


            return donationRepository.save(existingDonation);
        } else {
            return null;
        }
    }

    public boolean deleteDonation(Long id) {
        return donationRepository.findById(id).map(donation -> {
            donationRepository.delete(donation);
            return true;
        }).orElse(false);
    }
    public List<Donation> getDonationsByUserId(Long userId) {
        return donationRepository.findByDonorUserId(userId);
    }


}