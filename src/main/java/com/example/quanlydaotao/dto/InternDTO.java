package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.InternScore;

import java.time.LocalDate;
import java.util.List;

public class InternDTO {
    private String userName;
    private LocalDate starDate;
    private LocalDate endDate;
    private String trainingState;
    private Boolean isPass;
    private String scoreInTeam;
    private List<InternScore> subjects;

}
