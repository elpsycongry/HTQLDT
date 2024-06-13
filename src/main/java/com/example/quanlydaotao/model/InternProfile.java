package com.example.quanlydaotao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity // dùng để khai báo với Spring Boot rằng đây là 1 entity biểu diễn table trong db
@Data // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor
@Table(name = "intern_profiles")
public class InternProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "intern_id")
    private Intern intern;
    private LocalDate startDate;
    private LocalDate endDate;
    private String trainingState;
    private Boolean isPass;
    private String scoreInTeam;

    public InternProfile(Intern intern) {
        this.intern = intern;
        this.startDate = LocalDate.now();
        this.endDate = null;
        this.trainingState = "training";
        this.isPass = false;
        this.scoreInTeam = null;
    }
}
