package com.example.HopeConnect.DTO;

import com.example.HopeConnect.Enumes.OrphanageStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrphanageDTO {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String phone;
    private String email;
    private String contactPerson;
    private int capacity;
    private int currentOrphans;
    private OrphanageStatus status;
    private ManagerDTO manager;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
