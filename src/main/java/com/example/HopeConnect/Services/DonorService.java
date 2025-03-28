package com.example.HopeConnect.Services;

import com.example.HopeConnect.Models.Donor;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.DonorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonorService {
    @Autowired
    private DonorRepository donorRepository;

    public List<Donor> getAllDonors() {
        return donorRepository.findAll();
    }

    public Donor getDonorById(Long id) {
        return donorRepository.findById(id).orElse(null);
    }

    public Donor createDonor(Donor donor) {
        return donorRepository.save(donor);
    }

    /*public Donor updateDonor(Long id, Donor updatedDonor) {
        return donorRepository.findById(id).map(donor -> {
            donor.setTotalSupportedProjects(updatedDonor.getTotalSupportedProjects());
            donor.setDonorStatus(updatedDonor.getDonorStatus());
            return donorRepository.save(donor);
        }).orElse(null);
    }*/

    public Donor updateDonor(Long id, Donor updatedDonor) {
        return donorRepository.findById(id).map(donor -> {
            // Update user details
            if (updatedDonor.getUser() != null) {
                User existingUser = donor.getUser();
                User updatedUser = updatedDonor.getUser();

                existingUser.setName(updatedUser.getName());
                existingUser.setEmail(updatedUser.getEmail());
            }

            // Update donor details
            donor.setTotalSupportedProjects(updatedDonor.getTotalSupportedProjects());
            donor.setDonorStatus(updatedDonor.getDonorStatus());

            return donorRepository.save(donor);
        }).orElse(null);
    }
    public boolean deleteDonor(Long id) {
        return donorRepository.findById(id).map(donor -> {
            donorRepository.delete(donor);
            return true;
        }).orElse(false);
    }


}
