package com.capstone.ttp.entitiy;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Table(name="funding")
@Entity
public class Funding {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(name="program_name", nullable = false,length = 100)
    private String programName;

    @Column(name="funding_ministry", length = 100, nullable = true)
    private String fundingMinistry;

    @Column(name="funding_type",nullable = true)
    private String fundingType;

    @Column(name="funding_amount", nullable = false)
    private float fundingAmount;

    @Column(name="overview", nullable = true)
    private String overview;

    @Column(name="details")
    private String details;

    @Column(name="qualifications")
    private String qualifications;

    @Column(name="deadline")
    private Date deadline;

    @Column(name="status")
    private String status;

    @Column(name="org_type")
    private String orgType;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    public Funding(Integer id, String programName, String fundingMinistry, String fundingType, float fundingAmount, Date deadline, String overview, String details, String qualifications, String status, String orgType, Date createdAt) {
        this.id = id;
        this.programName = programName;
        this.fundingMinistry = fundingMinistry;
        this.fundingType = fundingType;
        this.fundingAmount = fundingAmount;
        this.overview = overview;
        this.details = details;
        this.qualifications = qualifications;
        this.deadline = deadline;
        this.status = status;
        this.orgType = orgType;
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public String getFundingMinistry() {
        return fundingMinistry;
    }

    public void setFundingMinistry(String fundingMinistry) {
        this.fundingMinistry = fundingMinistry;
    }

    public String getFundingType() {
        return fundingType;
    }

    public void setFundingType(String fundingType) {
        this.fundingType = fundingType;
    }

    public float getFundingAmount() {
        return fundingAmount;
    }

    public void setFundingAmount(float fundingAmount) {
        this.fundingAmount = fundingAmount;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public Funding() {

    }
}
