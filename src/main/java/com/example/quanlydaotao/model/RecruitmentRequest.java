package com.example.quanlydaotao.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class RecruitmentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private Users users;

    @Column(nullable = false)
    private LocalDateTime dateStart;

    @Column(nullable = false)
    private LocalDate dateEnd;

    @Column(unique = true, nullable = false)
    private String name;

    private String reason;
    private String division;
    private String status;
    @Override
    public String toString() {
        return "RecruitmentRequest{" +
                "id=" + id +
                ", users=" + users +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", name='" + name + '\'' +
                ", reason='" + reason + '\'' +
                ", division='" + division + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Long getId() {
        return id;
    }

    public RecruitmentRequest setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getName() {
        return name;
    }

    public RecruitmentRequest setName(String name) {
        this.name = name;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public RecruitmentRequest setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public String getDivision() {
        return division;
    }

    public RecruitmentRequest setDivision(String division) {
        this.division = division;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public RecruitmentRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
