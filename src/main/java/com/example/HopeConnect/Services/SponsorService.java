package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Sponsor;
import com.example.HopeConnect.Repositories.SponsorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SponsorService {

    @Autowired
    private SponsorRepository sponsorRepository;

    public List<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    public Optional<Sponsor> getSponsorById(Long id) {
        return sponsorRepository.findById(id);
    }

    public Optional<Sponsor> getSponsorByUserId(Long userId) {
        return sponsorRepository.findByUserId(userId);
    }

    public Sponsor saveSponsor(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    public void deleteSponsor(Long id) {
        sponsorRepository.deleteById(id);
    }
    public Optional<Sponsor> findById(Long id) {
        return sponsorRepository.findById(id);
    }



    @Autowired
    private NotificationService notificationService;

    public Sponsor createSponsor(Sponsor sponsor) {
        Sponsor savedSponsor = sponsorRepository.save(sponsor);

        notificationService.sendEmailNotification(
                savedSponsor.getUser().getEmail(),
                "تم تفعيل الكفالة",
                "شكراً لرعايتك ليتيم. سنوافيك بالتحديثات حول حالته."
        );

        return savedSponsor;
    }

}