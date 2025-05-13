package com.example.HopeConnect.Models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orphan_projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrphanProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orphanage_id", referencedColumnName = "id")
    private Orphanage orphanage;


    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer targetOrphans;

    private Integer sponsoredOrphans;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal targetAmount;

    private BigDecimal collectedAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationType donationType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupportType supportType;

    @Column(nullable = false)
    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    @OneToMany(mappedBy = "orphanProject")
    private List<Orphan> orphans;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }




    public enum DonationType {
        ONE_TIME, MONTHLY
    }

    public enum SupportType {
        FINANCIAL, EDUCATIONAL, HEALTHCARE, FULL_SPONSORSHIP
    }

    public enum ProjectStatus {
        OPEN, CLOSED, IN_PROGRESS
    }
}