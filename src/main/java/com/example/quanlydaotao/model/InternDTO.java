package com.example.quanlydaotao.model;

import org.springframework.data.relational.core.sql.In;

import java.time.LocalDate;
import java.util.List;

public class InternDTO {
    private Long id;
    private User user;
    private InternProfileDTO internProfileDTO;
    private List<InternScoreDTO> internScoreDTOList;

    public InternDTO(){}

    public InternDTO(Long id, User user, InternProfileDTO internProfileDTO, List<InternScoreDTO> internScoreDTOList) {
        this.id = id;
        this.user = user;
        this.internProfileDTO = internProfileDTO;
        this.internScoreDTOList = internScoreDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public InternProfileDTO getInternProfileDTO() {
        return internProfileDTO;
    }

    public void setInternProfileDTO(InternProfileDTO internProfileDTO) {
        this.internProfileDTO = internProfileDTO;
    }

    public List<InternScoreDTO> getInternScoreDTOList() {
        return internScoreDTOList;
    }

    public void setInternScoreDTOList(List<InternScoreDTO> internScoreDTOList) {
        this.internScoreDTOList = internScoreDTOList;
    }
}

