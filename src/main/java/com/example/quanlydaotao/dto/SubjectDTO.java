package com.example.quanlydaotao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private String name;
    private String theoryScore;
    private String practiceScore;
    private String attitudeScore;

}
