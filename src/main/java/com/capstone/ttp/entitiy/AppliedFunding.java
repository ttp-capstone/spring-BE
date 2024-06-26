package com.capstone.ttp.entitiy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name="applied_funding")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppliedFunding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "funding_id", referencedColumnName = "id")
    private Funding funding;

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;

    private String status;
    private String description;
    private Date applicationDate;
    private Date approvalDate;
    private Date rejectionDate;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;


}
