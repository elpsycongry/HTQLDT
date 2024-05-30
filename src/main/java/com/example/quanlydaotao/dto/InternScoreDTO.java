package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.InternSubject;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class InternScoreDTO {
    private Long id;
    private String value;
    private String type;
    private InternSubject internSubject;

    public InternScoreDTO() {
    }

    public InternScoreDTO(Long id, String value, String type, InternSubject internSubject) {
        this.id = id;
        this.value = value;
        this.type = type;
        this.internSubject = internSubject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public InternSubject getInternSubject() {
        return internSubject;
    }

    public void setInternSubject(InternSubject internSubject) {
        this.internSubject = internSubject;
    }
}
