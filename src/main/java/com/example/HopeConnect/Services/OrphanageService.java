package com.example.HopeConnect.Services;

import com.example.HopeConnect.DTO.OrphanageDTO;
import com.example.HopeConnect.DTO.ManagerDTO;
import com.example.HopeConnect.Enumes.OrphanageStatus;
import com.example.HopeConnect.Enumes.UserType;
import com.example.HopeConnect.Models.Orphanage;
import com.example.HopeConnect.Models.User;
import com.example.HopeConnect.Repositories.OrphanageRepository;
import com.example.HopeConnect.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrphanageService {

    @Autowired
    private OrphanageRepository orphanageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<OrphanageDTO> getAllOrphanages() {
        return orphanageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrphanageDTO> getOrphanageById(Long id) {
        return orphanageRepository.findById(id).map(this::convertToDTO);
    }

    public List<OrphanageDTO> getOrphanagesByCity(String city) {
        return orphanageRepository.findByCity(city).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrphanageDTO> getOrphanagesByStatus(OrphanageStatus status) {
        return orphanageRepository.findByStatus(status).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrphanageDTO createOrphanage(OrphanageDTO orphanageDTO, Long managerId) {
        Optional<User> userOpt = userRepository.findById(managerId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("Error: Manager ID " + managerId + " does not exist.");
        }

        User manager = userOpt.get();

        if (manager.getUserType() != UserType.ORPHANAGE_MANAGER) {
            throw new RuntimeException("Error: User ID " + managerId + " is not an orphanage manager.");
        }

        Orphanage orphanage = convertToEntity(orphanageDTO);
        orphanage.setManager(manager);
        orphanage = orphanageRepository.save(orphanage);

        return convertToDTO(orphanage);
    }

    public String deleteOrphanage(Long id) {
        if (orphanageRepository.existsById(id)) {
            orphanageRepository.deleteById(id);
            return "Orphanage deleted successfully.";
        } else {
            return "Error: Orphanage not found.";
        }
    }

    private OrphanageDTO convertToDTO(Orphanage orphanage) {
        ManagerDTO managerDTO = new ManagerDTO(
                orphanage.getManager().getId(),
                orphanage.getManager().getName(),
                orphanage.getManager().getEmail()
        );

        return new OrphanageDTO(
                orphanage.getId(),
                orphanage.getName(),
                orphanage.getAddress(),
                orphanage.getCity(),
                orphanage.getPhone(),
                orphanage.getEmail(),
                orphanage.getContactPerson(),
                orphanage.getCapacity(),
                orphanage.getCurrentOrphans(),
                orphanage.getStatus(),
                managerDTO,
                orphanage.getCreatedAt(),
                orphanage.getUpdatedAt()
        );
    }

    private Orphanage convertToEntity(OrphanageDTO orphanageDTO) {
        Orphanage orphanage = new Orphanage();
        orphanage.setId(orphanageDTO.getId());
        orphanage.setName(orphanageDTO.getName());
        orphanage.setAddress(orphanageDTO.getAddress());
        orphanage.setCity(orphanageDTO.getCity());
        orphanage.setPhone(orphanageDTO.getPhone());
        orphanage.setEmail(orphanageDTO.getEmail());
        orphanage.setContactPerson(orphanageDTO.getContactPerson());
        orphanage.setCapacity(orphanageDTO.getCapacity());
        orphanage.setCurrentOrphans(orphanageDTO.getCurrentOrphans());
        orphanage.setStatus(orphanageDTO.getStatus());
        return orphanage;
    }
}
