package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingStatsImpl implements TrainingStatsService {
    @Autowired
    InternService internService;
    @Autowired
    InternProfileRepository profileRepository;
    @Override
    public TrainingStatsDTO getTrainingStats() {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();

        List<InternProfile> profiles = profileRepository.findAll();
        trainingStatsDTO.setInternsEnrolled(profiles.size() + " TTS");

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        trainingStatsDTO.setGraduatingInterns(profilesPass.size() + " TTS");

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEquals(false);
        trainingStatsDTO.setInternsFailed(profilesFailed.size() + " TTS");

        if (profilesFailed.size() > 0) {
            trainingStatsDTO.setRate(profilesPass.size() / profilesFailed.size());
        }
        trainingStatsDTO.setRate(0);

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("Dang thuc tap");
        trainingStatsDTO.setInternsCurrentlyPracticing(profilesCurrent.size() + " TTS");
        List<Double> listScore = new ArrayList<>();
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "Da thuc tap");
        internDTOIterable.forEach(
                internDTO -> {
                    listScore.add(Double.valueOf(internDTO.getFinalScore()));
                }
        );
        for (Double score : listScore){
            totalScore = totalScore + score;
        }
        totalScore = totalScore / listScore.size();
        DecimalFormat df = new DecimalFormat("#.##");
        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        return trainingStatsDTO;
    }
}
