package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.Intern;

public class InternDTO {
    private long idRecruitment;
    private Intern intern;

    public long getIdRecruitment() {
        return idRecruitment;
    }

    public InternDTO setIdRecruitment(long idRecruitment) {
        this.idRecruitment = idRecruitment;
        return this;
    }

    public Intern getIntern() {
        return intern;
    }

    public InternDTO setIntern(Intern intern) {
        this.intern = intern;
        return this;
    }
}
