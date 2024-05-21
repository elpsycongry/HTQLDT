package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.TrainingStatsDTO;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.TrainingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.QuadCurve2D;
import java.text.DecimalFormat;
import java.time.temporal.IsoFields;
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
        DecimalFormat df = new DecimalFormat("#.##");
        DecimalFormat newDf = new DecimalFormat("#.#");

        List<InternProfile> profiles = profileRepository.findAll();
        trainingStatsDTO.setInternsEnrolled(profiles.size() + " TTS");

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        trainingStatsDTO.setGraduatingInterns(profilesPass.size() + " TTS");

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        trainingStatsDTO.setInternsFailed(profilesFailed.size() + " TTS");

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        trainingStatsDTO.setInternsCurrentlyPracticing(profilesCurrent.size() + " TTS");

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        trainingStatsDTO.setInternsQuitInternship(listInternQuitInternship.size() + " TTS");

        if (profilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(profilesPass.size()) / (Double.valueOf(profilesFailed.size()) +  Double.valueOf(listInternQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass()){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                    }
                }
        );
        for (Double score : listScore){
            totalScore = totalScore + score;
        }
        totalScore = totalScore / listScore.size();
        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        return trainingStatsDTO;
    }

    @Override
    public TrainingStatsDTO getTrainingStatsWithMonth(int month) {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();
        DecimalFormat newDf = new DecimalFormat("#.#");
        List<InternProfile> profiles = profileRepository.findAll();
        List<InternProfile> newProfiels = new ArrayList<>();
        for (InternProfile profile : profiles) {
            if (profile.getStartDate().getMonthValue() == month){
                newProfiels.add(profile);
            }
        }
        trainingStatsDTO.setInternsEnrolled(newProfiels.size() + " TTS");

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if (profile.getStartDate().getMonthValue() == month){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size() + " TTS");

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if (profile.getStartDate().getMonthValue() == month){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size() + " TTS");

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if (profile.getStartDate().getMonthValue() == month){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size() + " TTS");

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if (profile.getStartDate().getMonthValue() == month){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size() + " TTS");

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && internDTO.getStartDate().getMonthValue() == month){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                    }
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

    @Override
    public TrainingStatsDTO getTrainingStatsWithQuarter(int quarter) {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();
        DecimalFormat newDf = new DecimalFormat("#.#");

        List<InternProfile> profiles = profileRepository.findAll();
        List<InternProfile> newProfiels = new ArrayList<>();
        for (InternProfile profile : profiles) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter){
                newProfiels.add(profile);
            }
        }
        trainingStatsDTO.setInternsEnrolled(newProfiels.size() + " TTS");

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size() + " TTS");

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size() + " TTS");

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size() + " TTS");

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size() + " TTS");

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && (internDTO.getStartDate().getMonthValue() / 3 ) + 1 == quarter){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                    }
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

    @Override
    public TrainingStatsDTO getTrainingStatsWithYear(int year) {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();
        DecimalFormat newDf = new DecimalFormat("#.#");

        List<InternProfile> profiles = profileRepository.findAll();
        List<InternProfile> newProfiels = new ArrayList<>();
        for (InternProfile profile : profiles) {
            if (profile.getStartDate().getYear() == year){
                newProfiels.add(profile);
            }
        }
        trainingStatsDTO.setInternsEnrolled(newProfiels.size() + " TTS");

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if (profile.getStartDate().getYear() == year){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size() + " TTS");

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if (profile.getStartDate().getYear() == year){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size() + " TTS");

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if (profile.getStartDate().getYear() == year){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size() + " TTS");

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if (profile.getStartDate().getYear() == year){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size() + " TTS");

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && internDTO.getStartDate().getYear() == year){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                    }
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
