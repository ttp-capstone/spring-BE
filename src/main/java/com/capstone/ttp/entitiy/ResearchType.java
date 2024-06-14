package com.capstone.ttp.entitiy;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name="research_types")
@Entity
public class ResearchType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    private String title;
    private String description;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public ResearchType(Integer id, String title, String description, Date createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
    }

    public ResearchType() {
    }
}
