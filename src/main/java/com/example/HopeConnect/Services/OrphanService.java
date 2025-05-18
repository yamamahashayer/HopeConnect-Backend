package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Orphan;
import com.example.HopeConnect.Repositories.OrphanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrphanService {

    @Autowired
    private OrphanRepository orphanRepository;
    public Optional<Orphan> findById(Long id) {
        return orphanRepository.findById(id);
    }

    // الحصول على جميع الأيتام
    public List<Orphan> getAllOrphans() {
        return orphanRepository.findAll();
    }

    // الحصول على يتيم حسب ID
    public Optional<Orphan> getOrphanById(Long id) {
        return orphanRepository.findById(id);
    }

    // حفظ يتيم
    public Orphan saveOrphan(Orphan orphan) {
        return orphanRepository.save(orphan);
    }

    // حذف يتيم
    public void deleteOrphan(Long id) {
        orphanRepository.deleteById(id);
    }

  /*  @Autowired
    private NotificationService notificationService;

    public Orphan updateOrphan(Orphan orphan) {
        Orphan updatedOrphan = orphanRepository.save(orphan);

        if ("Guaranteed".equals(updatedOrphan.getStatus())) {
            notificationService.sendEmailNotification(
                    updatedOrphan.getSponsor().getUser().getEmail(),
                    "تم كفالة اليتيم",
                    "تم ربطك بهذا اليتيم بنجاح، شكراً لك ❤️"
            );
        }

        return updatedOrphan;
    }*/
  public Optional<Orphan> findById(Long id) {
      return orphanRepository.findById(id);
  }

}