package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.SubjectComment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private String name;
    private String theoryScore;
    private String practiceScore;
    private String attitudeScore;
    private SubjectComment comment;

}
