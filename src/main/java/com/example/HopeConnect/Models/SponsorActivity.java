package com.example.HopeConnect.Models;

import com.example.HopeConnect.Enumes.ActivityType;
import jakarta.persistence.*;

@Entity
public class SponsorActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Sponsor sponsor;  // الراعي (إجباري)

    @ManyToOne(optional = true)
    private Orphanage orphanage;  // دار الأيتام (اختياري)

    @ManyToOne(optional = true)
    private Orphan orphan;  // يتيم (اختياري)

    @ManyToOne(optional = true)
    private OrphanProject project;  // مشروع (اختياري)

    @Enumerated(EnumType.STRING)
    private ActivityType activityType;  // نوع النشاط (إجباري)

    private String activityDescription;

    // Getters and Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Sponsor getSponsor() { return  sponsor; }
    public void setSponsor( Sponsor sponsor) { this.sponsor = sponsor; }

    public Orphanage getOrphanage() { return orphanage; }
    public void setOrphanage(Orphanage orphanage) { this.orphanage = orphanage; }

    public Orphan getOrphan() { return orphan; }
    public void setOrphan(Orphan orphan) { this.orphan = orphan; }

    public OrphanProject getProject() { return project; }
    public void setProject(OrphanProject project) { this.project = project; }

    public ActivityType getActivityType() { return activityType; }
    public void setActivityType(ActivityType activityType) { this.activityType = activityType; }

    public String getActivityDescription() { return activityDescription; }
    public void setActivityDescription(String activityDescription) { this.activityDescription = activityDescription; }

}
