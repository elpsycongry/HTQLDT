package com.example.quanlydaotao.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // dùng để khai báo với Spring Boot rằng đây là 1 entity biểu diễn table trong db
@Data // annotation này sẽ tự động khai báo getter và setter cho class
@AllArgsConstructor // dùng để khai báo constructor với tất cả các properties
@NoArgsConstructor
@Table(name = "intern_scores")
public class InternScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "intern_id")
    private Intern intern;

    private String value;
    private String type;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private InternSubject internSubject;
}
