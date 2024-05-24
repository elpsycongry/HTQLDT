package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.AverageOfSubjectDTO;
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
        trainingStatsDTO.setInternsEnrolled(profiles.size());

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        trainingStatsDTO.setGraduatingInterns(profilesPass.size());

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        trainingStatsDTO.setInternsFailed(profilesFailed.size());

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        trainingStatsDTO.setInternsCurrentlyPracticing(profilesCurrent.size());

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        trainingStatsDTO.setInternsQuitInternship(listInternQuitInternship.size());

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
    public TrainingStatsDTO getTrainingStatsWithMonth(int month, int year) {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();
        DecimalFormat newDf = new DecimalFormat("#.#");
        List<InternProfile> profiles = profileRepository.findAll();
        List<InternProfile> newProfiels = new ArrayList<>();
        for (InternProfile profile : profiles) {
            if (profile.getStartDate().getMonthValue() == month && profile.getStartDate().getYear() == year){
                newProfiels.add(profile);
            }
        }
        trainingStatsDTO.setInternsEnrolled(newProfiels.size());

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if (profile.getStartDate().getMonthValue() == month && profile.getStartDate().getYear() == year){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size());

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if (profile.getStartDate().getMonthValue() == month && profile.getStartDate().getYear() == year){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size());

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if (profile.getStartDate().getMonthValue() == month && profile.getStartDate().getYear() == year){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size());

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if (profile.getStartDate().getMonthValue() == month && profile.getStartDate().getYear() == year){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size());

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();

        List<AverageOfSubjectDTO> listAverageOfSubjectDTO = new ArrayList<>();

        AverageOfSubjectDTO averageOfJavaDTO = new AverageOfSubjectDTO();
        averageOfJavaDTO.setSubjectName("Java");
        averageOfJavaDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGitDTO = new AverageOfSubjectDTO();
        averageOfGitDTO.setSubjectName("Git");
        averageOfGitDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfReactDTO = new AverageOfSubjectDTO();
        averageOfReactDTO.setSubjectName("React");
        averageOfReactDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfSpringBootDTO = new AverageOfSubjectDTO();
        averageOfSpringBootDTO.setSubjectName("Spring boot");
        averageOfSpringBootDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfCTDLDTO = new AverageOfSubjectDTO();
        averageOfCTDLDTO.setSubjectName("CTDL");
        averageOfCTDLDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGiaiThuatDTO = new AverageOfSubjectDTO();
        averageOfGiaiThuatDTO.setSubjectName("Giải thuật");
        averageOfGiaiThuatDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfScrumDTO = new AverageOfSubjectDTO();
        averageOfScrumDTO.setSubjectName("Scrum");
        averageOfScrumDTO.setAverage(0.0);

        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && internDTO.getStartDate().getMonthValue() == month && internDTO.getStartDate().getYear() == year){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                        internDTO.getInternSubjectDTOList().forEach(
                                internSubjectDTO -> {
                                    if (internSubjectDTO.getNameSubject().equals("Java")){
                                        averageOfJavaDTO.setAverage((averageOfJavaDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Git")){
                                        averageOfGitDTO.setAverage((averageOfGitDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("React")){
                                        averageOfReactDTO.setAverage((averageOfReactDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Spring boot")){
                                        averageOfSpringBootDTO.setAverage((averageOfSpringBootDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("CTDL")){
                                        averageOfCTDLDTO.setAverage((averageOfCTDLDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Giải thuật")){
                                        averageOfGiaiThuatDTO.setAverage((averageOfGiaiThuatDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Scrum")){
                                        averageOfScrumDTO.setAverage((averageOfScrumDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                }
                        );
                    }
                }
        );
        for (Double score : listScore){
            totalScore = totalScore + score;
        }
        DecimalFormat df = new DecimalFormat("#.##");
        totalScore = totalScore / listScore.size();
        if (listScore.size() > 0){
            averageOfJavaDTO.setAverage(Double.valueOf(df.format(averageOfJavaDTO.getAverage() / listScore.size())));
            averageOfGitDTO.setAverage(Double.valueOf(df.format(averageOfGitDTO.getAverage() / listScore.size())));
            averageOfReactDTO.setAverage(Double.valueOf(df.format(averageOfReactDTO.getAverage() / listScore.size())));
            averageOfCTDLDTO.setAverage(Double.valueOf(df.format(averageOfCTDLDTO.getAverage() / listScore.size())));
            averageOfGiaiThuatDTO.setAverage(Double.valueOf(df.format(averageOfGiaiThuatDTO.getAverage() / listScore.size())));
            averageOfScrumDTO.setAverage(Double.valueOf(df.format(averageOfScrumDTO.getAverage() / listScore.size())));
            averageOfSpringBootDTO.setAverage(Double.valueOf(df.format(averageOfSpringBootDTO.getAverage() / listScore.size())));
        }else {
            averageOfJavaDTO.setAverage(0.0);
            averageOfGitDTO.setAverage(0.0);
            averageOfReactDTO.setAverage(0.0);
            averageOfCTDLDTO.setAverage(0.0);
            averageOfGiaiThuatDTO.setAverage(0.0);
            averageOfScrumDTO.setAverage(0.0);
            averageOfSpringBootDTO.setAverage(0.0);
        }



        listAverageOfSubjectDTO.add(averageOfJavaDTO);
        listAverageOfSubjectDTO.add(averageOfGitDTO);
        listAverageOfSubjectDTO.add(averageOfReactDTO);
        listAverageOfSubjectDTO.add(averageOfSpringBootDTO);
        listAverageOfSubjectDTO.add(averageOfCTDLDTO);
        listAverageOfSubjectDTO.add(averageOfGiaiThuatDTO);
        listAverageOfSubjectDTO.add(averageOfScrumDTO);

        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        trainingStatsDTO.setAverageOfSubject(listAverageOfSubjectDTO);
        return trainingStatsDTO;
    }

    @Override
    public TrainingStatsDTO getTrainingStatsWithQuarter(int quarter, int year) {
        TrainingStatsDTO trainingStatsDTO = new TrainingStatsDTO();
        DecimalFormat newDf = new DecimalFormat("#.#");

        List<InternProfile> profiles = profileRepository.findAll();
        List<InternProfile> newProfiels = new ArrayList<>();
        for (InternProfile profile : profiles) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter && profile.getStartDate().getYear() == year){
                newProfiels.add(profile);
            }
        }
        trainingStatsDTO.setInternsEnrolled(newProfiels.size());

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter && profile.getStartDate().getYear() == year){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size());

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter && profile.getStartDate().getYear() == year){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size());

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter && profile.getStartDate().getYear() == year){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size());

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if ((profile.getStartDate().getMonthValue() /3 ) + 1 == quarter && profile.getStartDate().getYear() == year){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size());

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();

        List<AverageOfSubjectDTO> listAverageOfSubjectDTO = new ArrayList<>();

        AverageOfSubjectDTO averageOfJavaDTO = new AverageOfSubjectDTO();
        averageOfJavaDTO.setSubjectName("Java");
        averageOfJavaDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGitDTO = new AverageOfSubjectDTO();
        averageOfGitDTO.setSubjectName("Git");
        averageOfGitDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfReactDTO = new AverageOfSubjectDTO();
        averageOfReactDTO.setSubjectName("React");
        averageOfReactDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfSpringBootDTO = new AverageOfSubjectDTO();
        averageOfSpringBootDTO.setSubjectName("Spring boot");
        averageOfSpringBootDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfCTDLDTO = new AverageOfSubjectDTO();
        averageOfCTDLDTO.setSubjectName("CTDL");
        averageOfCTDLDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGiaiThuatDTO = new AverageOfSubjectDTO();
        averageOfGiaiThuatDTO.setSubjectName("Giải thuật");
        averageOfGiaiThuatDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfScrumDTO = new AverageOfSubjectDTO();
        averageOfScrumDTO.setSubjectName("Scrum");
        averageOfScrumDTO.setAverage(0.0);
        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && (internDTO.getStartDate().getMonthValue() / 3 ) + 1 == quarter && internDTO.getStartDate().getYear() == year){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                        internDTO.getInternSubjectDTOList().forEach(
                                internSubjectDTO -> {
                                    if (internSubjectDTO.getNameSubject().equals("Java")){
                                        averageOfJavaDTO.setAverage((averageOfJavaDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Git")){
                                        averageOfGitDTO.setAverage((averageOfGitDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("React")){
                                        averageOfReactDTO.setAverage((averageOfReactDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Spring boot")){
                                        averageOfSpringBootDTO.setAverage((averageOfSpringBootDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("CTDL")){
                                        averageOfCTDLDTO.setAverage((averageOfCTDLDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Giải thuật")){
                                        averageOfGiaiThuatDTO.setAverage((averageOfGiaiThuatDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Scrum")){
                                        averageOfScrumDTO.setAverage((averageOfScrumDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                }
                        );
                    }
                }
        );
        for (Double score : listScore){
            totalScore = totalScore + score;
        }
        totalScore = totalScore / listScore.size();
        DecimalFormat df = new DecimalFormat("#.##");
        if (listScore.size() > 0){
            averageOfJavaDTO.setAverage(Double.valueOf(df.format(averageOfJavaDTO.getAverage() / listScore.size())));
            averageOfGitDTO.setAverage(Double.valueOf(df.format(averageOfGitDTO.getAverage() / listScore.size())));
            averageOfReactDTO.setAverage(Double.valueOf(df.format(averageOfReactDTO.getAverage() / listScore.size())));
            averageOfCTDLDTO.setAverage(Double.valueOf(df.format(averageOfCTDLDTO.getAverage() / listScore.size())));
            averageOfGiaiThuatDTO.setAverage(Double.valueOf(df.format(averageOfGiaiThuatDTO.getAverage() / listScore.size())));
            averageOfScrumDTO.setAverage(Double.valueOf(df.format(averageOfScrumDTO.getAverage() / listScore.size())));
            averageOfSpringBootDTO.setAverage(Double.valueOf(df.format(averageOfSpringBootDTO.getAverage() / listScore.size())));
        }else {
            averageOfJavaDTO.setAverage(0.0);
            averageOfGitDTO.setAverage(0.0);
            averageOfReactDTO.setAverage(0.0);
            averageOfCTDLDTO.setAverage(0.0);
            averageOfGiaiThuatDTO.setAverage(0.0);
            averageOfScrumDTO.setAverage(0.0);
            averageOfSpringBootDTO.setAverage(0.0);
        }



        listAverageOfSubjectDTO.add(averageOfJavaDTO);
        listAverageOfSubjectDTO.add(averageOfGitDTO);
        listAverageOfSubjectDTO.add(averageOfReactDTO);
        listAverageOfSubjectDTO.add(averageOfSpringBootDTO);
        listAverageOfSubjectDTO.add(averageOfCTDLDTO);
        listAverageOfSubjectDTO.add(averageOfGiaiThuatDTO);
        listAverageOfSubjectDTO.add(averageOfScrumDTO);
        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        trainingStatsDTO.setAverageOfSubject(listAverageOfSubjectDTO);
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
        trainingStatsDTO.setInternsEnrolled(newProfiels.size());

        List<InternProfile> profilesPass = profileRepository.findByIsPassEquals(true);
        List<InternProfile> newProfilesPass = new ArrayList<>();
        for (InternProfile profile : profilesPass) {
            if (profile.getStartDate().getYear() == year){
                newProfilesPass.add(profile);
            }
        }
        trainingStatsDTO.setGraduatingInterns(newProfilesPass.size());

        List<InternProfile> profilesFailed = profileRepository.findByIsPassEqualsAndTrainingStateEquals(false, "trained");
        List<InternProfile> newProfilesFailed = new ArrayList<>();
        for (InternProfile profile : profilesFailed) {
            if (profile.getStartDate().getYear() == year){
                newProfilesFailed.add(profile);
            }
        }
        trainingStatsDTO.setInternsFailed(newProfilesFailed.size());

        List<InternProfile> profilesCurrent = profileRepository.findByTrainingStateEquals("training");
        List<InternProfile> newProfilesCurrent = new ArrayList<>();
        for (InternProfile profile : profilesCurrent) {
            if (profile.getStartDate().getYear() == year){
                newProfilesCurrent.add(profile);
            }
        }
        trainingStatsDTO.setInternsCurrentlyPracticing(newProfilesCurrent.size());

        List<InternProfile> listInternQuitInternship = profileRepository.findByTrainingStateEquals("stop_training");
        List<InternProfile> newProfilesQuitInternship = new ArrayList<>();
        for (InternProfile profile : listInternQuitInternship) {
            if (profile.getStartDate().getYear() == year){
                newProfilesQuitInternship.add(profile);
            }
        }
        trainingStatsDTO.setInternsQuitInternship(newProfilesQuitInternship.size());

        if (newProfilesFailed.size() > 0) {
            trainingStatsDTO.setRate(Double.valueOf(newDf.format((Double.valueOf(newProfilesPass.size()) / (Double.valueOf(newProfilesFailed.size()) +  Double.valueOf(newProfilesQuitInternship.size()))))));
        }else {
            trainingStatsDTO.setRate(0.0);
        }

        List<Double> listScore = new ArrayList<>();

        List<AverageOfSubjectDTO> listAverageOfSubjectDTO = new ArrayList<>();

        AverageOfSubjectDTO averageOfJavaDTO = new AverageOfSubjectDTO();
        averageOfJavaDTO.setSubjectName("Java");
        averageOfJavaDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGitDTO = new AverageOfSubjectDTO();
        averageOfGitDTO.setSubjectName("Git");
        averageOfGitDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfReactDTO = new AverageOfSubjectDTO();
        averageOfReactDTO.setSubjectName("React");
        averageOfReactDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfSpringBootDTO = new AverageOfSubjectDTO();
        averageOfSpringBootDTO.setSubjectName("Spring boot");
        averageOfSpringBootDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfCTDLDTO = new AverageOfSubjectDTO();
        averageOfCTDLDTO.setSubjectName("CTDL");
        averageOfCTDLDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfGiaiThuatDTO = new AverageOfSubjectDTO();
        averageOfGiaiThuatDTO.setSubjectName("Giải thuật");
        averageOfGiaiThuatDTO.setAverage(0.0);
        AverageOfSubjectDTO averageOfScrumDTO = new AverageOfSubjectDTO();
        averageOfScrumDTO.setSubjectName("Scrum");
        averageOfScrumDTO.setAverage(0.0);

        Double totalScore = 0.0;
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    if (internDTO.getPass() && internDTO.getStartDate().getYear() == year){
                        Double score = (Double.valueOf(internDTO.getFinalScore()) + Double.valueOf(internDTO.getScoreInTeam())) / 2;
                        listScore.add(score);
                        internDTO.getInternSubjectDTOList().forEach(
                                internSubjectDTO -> {
                                    if (internSubjectDTO.getNameSubject().equals("Java")){
                                        averageOfJavaDTO.setAverage((averageOfJavaDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Git")){
                                        averageOfGitDTO.setAverage((averageOfGitDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("React")){
                                        averageOfReactDTO.setAverage((averageOfReactDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Spring boot")){
                                        averageOfSpringBootDTO.setAverage((averageOfSpringBootDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("CTDL")){
                                        averageOfCTDLDTO.setAverage((averageOfCTDLDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Giải thuật")){
                                        averageOfGiaiThuatDTO.setAverage((averageOfGiaiThuatDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                    if (internSubjectDTO.getNameSubject().equals("Scrum")){
                                        averageOfScrumDTO.setAverage((averageOfScrumDTO.getAverage() + Double.valueOf(internSubjectDTO.getTotalScore())));
                                    }
                                }
                        );
                    }
                }
        );
        for (Double score : listScore){
            totalScore = totalScore + score;
        }
        totalScore = totalScore / listScore.size();
        DecimalFormat df = new DecimalFormat("#.##");
        if (listScore.size() > 0){
            averageOfJavaDTO.setAverage(Double.valueOf(df.format(averageOfJavaDTO.getAverage() / listScore.size())));
            averageOfGitDTO.setAverage(Double.valueOf(df.format(averageOfGitDTO.getAverage() / listScore.size())));
            averageOfReactDTO.setAverage(Double.valueOf(df.format(averageOfReactDTO.getAverage() / listScore.size())));
            averageOfCTDLDTO.setAverage(Double.valueOf(df.format(averageOfCTDLDTO.getAverage() / listScore.size())));
            averageOfGiaiThuatDTO.setAverage(Double.valueOf(df.format(averageOfGiaiThuatDTO.getAverage() / listScore.size())));
            averageOfScrumDTO.setAverage(Double.valueOf(df.format(averageOfScrumDTO.getAverage() / listScore.size())));
            averageOfSpringBootDTO.setAverage(Double.valueOf(df.format(averageOfSpringBootDTO.getAverage() / listScore.size())));
        }else {
            averageOfJavaDTO.setAverage(0.0);
            averageOfGitDTO.setAverage(0.0);
            averageOfReactDTO.setAverage(0.0);
            averageOfCTDLDTO.setAverage(0.0);
            averageOfGiaiThuatDTO.setAverage(0.0);
            averageOfScrumDTO.setAverage(0.0);
            averageOfSpringBootDTO.setAverage(0.0);
        }

        listAverageOfSubjectDTO.add(averageOfJavaDTO);
        listAverageOfSubjectDTO.add(averageOfGitDTO);
        listAverageOfSubjectDTO.add(averageOfReactDTO);
        listAverageOfSubjectDTO.add(averageOfSpringBootDTO);
        listAverageOfSubjectDTO.add(averageOfCTDLDTO);
        listAverageOfSubjectDTO.add(averageOfGiaiThuatDTO);
        listAverageOfSubjectDTO.add(averageOfScrumDTO);

        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        trainingStatsDTO.setAverageOfSubject(listAverageOfSubjectDTO);

        trainingStatsDTO.setAverageGraduationScore(Double.parseDouble(df.format(totalScore)));
        trainingStatsDTO.setAverageOfSubject(listAverageOfSubjectDTO);
        return trainingStatsDTO;
    }

    @Override
    public List<AverageOfSubjectDTO> getAllAverageOfSubject() {
        List<AverageOfSubjectDTO> averageOfSubjectDTOList = new ArrayList<>();
        Iterable<InternDTO> internDTOIterable = internService.findListInterWithNameInternAndTrainingState("", "trained");
        internDTOIterable.forEach(
                internDTO -> {
                    internDTO.getInternSubjectDTOList().forEach(
                            subjectDTO -> {
                                if (internDTO.getPass()){
                                    AverageOfSubjectDTO averageOfSubjectDTO = new AverageOfSubjectDTO();
                                    averageOfSubjectDTO.setSubjectName(subjectDTO.getNameSubject());
                                    averageOfSubjectDTO.setAverage(Double.valueOf(subjectDTO.getTotalScore()));
                                    averageOfSubjectDTOList.add(averageOfSubjectDTO);
                                }
                            }
                    );
                }
        );
        Double averageOfJava = 0.0;
        List<Double> listScoreOfJava = new ArrayList<>();
        averageOfSubjectDTOList.forEach(
                averageOfSubjectDTO -> {
                    if (averageOfSubjectDTO.getSubjectName().equals("Java")){
                        listScoreOfJava.add(averageOfSubjectDTO.getAverage());
                    }
                }
        );

        for (Double Score : listScoreOfJava){
            averageOfJava = averageOfJava + Score;
        }

        List<AverageOfSubjectDTO> listAverageOfSubjectDTO = new ArrayList<>();
        AverageOfSubjectDTO averageOfSubjectDTO = new AverageOfSubjectDTO();
        averageOfSubjectDTO.setSubjectName("Java");
        averageOfSubjectDTO.setAverage(Double.valueOf(averageOfJava));


        return averageOfSubjectDTOList;
    }

    @Override
    public TrainingStatsDTO getMaxTrainingStatsWithYear() {
        List<TrainingStatsDTO> trainingStatsDTOList = new ArrayList<>();
        TrainingStatsDTO trainingStatsDTO2020 = getTrainingStatsWithYear(2020);
        TrainingStatsDTO trainingStatsDTO2021 = getTrainingStatsWithYear(2021);
        TrainingStatsDTO trainingStatsDTO2022 = getTrainingStatsWithYear(2022);
        TrainingStatsDTO trainingStatsDTO2023 = getTrainingStatsWithYear(2023);
        TrainingStatsDTO trainingStatsDTO2024 = getTrainingStatsWithYear(2024);
        trainingStatsDTOList.add(trainingStatsDTO2020);
        trainingStatsDTOList.add(trainingStatsDTO2021);
        trainingStatsDTOList.add(trainingStatsDTO2022);
        trainingStatsDTOList.add(trainingStatsDTO2023);
        trainingStatsDTOList.add(trainingStatsDTO2024);
        TrainingStatsDTO trainingStatsMaxDTO = new TrainingStatsDTO();
        int internsEnrolled = trainingStatsDTOList.get(0).getInternsEnrolled();
        int graduatingInterns = trainingStatsDTOList.get(0).getGraduatingInterns();
        int internsFailed = trainingStatsDTOList.get(0).getInternsFailed();
        double rate = trainingStatsDTOList.get(0).getRate();
        double averageGraduationScore = trainingStatsDTOList.get(0).getAverageGraduationScore();
        double scoreJava = trainingStatsDTOList.get(0).getAverageOfSubject().get(0).getAverage();
        double scoreGit = trainingStatsDTOList.get(0).getAverageOfSubject().get(1).getAverage();
        double scoreReact = trainingStatsDTOList.get(0).getAverageOfSubject().get(2).getAverage();
        double scoreSpring = trainingStatsDTOList.get(0).getAverageOfSubject().get(3).getAverage();
        double scoreCTDL = trainingStatsDTOList.get(0).getAverageOfSubject().get(4).getAverage();
        double scoreGiaiThuat = trainingStatsDTOList.get(0).getAverageOfSubject().get(5).getAverage();
        double scoreScrum = trainingStatsDTOList.get(0).getAverageOfSubject().get(6).getAverage();
        for (TrainingStatsDTO trainingStatsDTO : trainingStatsDTOList) {
            if (internsEnrolled < trainingStatsDTO.getInternsEnrolled()) {
                internsEnrolled = trainingStatsDTO.getInternsEnrolled();
            }
            if (graduatingInterns < trainingStatsDTO.getGraduatingInterns()){
                graduatingInterns = trainingStatsDTO.getGraduatingInterns();
            }
            if (internsFailed < trainingStatsDTO.getInternsFailed()) {
                internsFailed = trainingStatsDTO.getInternsFailed();
            }
            if (rate < trainingStatsDTO.getRate()) {
                rate = trainingStatsDTO.getRate();
            }
            if (averageGraduationScore < trainingStatsDTO.getAverageGraduationScore()) {
                averageGraduationScore = trainingStatsDTO.getAverageGraduationScore();
            }
            if (scoreJava < trainingStatsDTO.getAverageOfSubject().get(0).getAverage()) {
                scoreJava = trainingStatsDTO.getAverageOfSubject().get(0).getAverage();
            }
            if (scoreGit < trainingStatsDTO.getAverageOfSubject().get(1).getAverage()) {
                scoreGit = trainingStatsDTO.getAverageOfSubject().get(1).getAverage();
            }
            if (scoreReact < trainingStatsDTO.getAverageOfSubject().get(2).getAverage()) {
                scoreReact = trainingStatsDTO.getAverageOfSubject().get(2).getAverage();
            }
            if (scoreSpring < trainingStatsDTO.getAverageOfSubject().get(3).getAverage()) {
                scoreSpring = trainingStatsDTO.getAverageOfSubject().get(3).getAverage();
            }
            if (scoreCTDL < trainingStatsDTO.getAverageOfSubject().get(4).getAverage()) {
                scoreCTDL = trainingStatsDTO.getAverageOfSubject().get(4).getAverage();
            }
            if (scoreGiaiThuat < trainingStatsDTO.getAverageOfSubject().get(5).getAverage()) {
                scoreGiaiThuat  = trainingStatsDTO.getAverageOfSubject().get(5).getAverage();
            }
            if (scoreScrum < trainingStatsDTO.getAverageOfSubject().get(6).getAverage()) {
                scoreScrum = trainingStatsDTO.getAverageOfSubject().get(6).getAverage();
            }
        }

        trainingStatsMaxDTO.setInternsEnrolled(internsEnrolled);
        trainingStatsMaxDTO.setGraduatingInterns(graduatingInterns);
        trainingStatsMaxDTO.setInternsFailed(internsFailed);
        trainingStatsMaxDTO.setAverageGraduationScore(averageGraduationScore);
        trainingStatsMaxDTO.setRate(rate);
        List<AverageOfSubjectDTO> listAverageOfSubjectDTO = new ArrayList<>();

        AverageOfSubjectDTO averageOfSubjectDTO = new AverageOfSubjectDTO();
        averageOfSubjectDTO.setSubjectName("Java");
        averageOfSubjectDTO.setAverage(scoreJava);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO);

        AverageOfSubjectDTO averageOfSubjectDTO1 = new AverageOfSubjectDTO();
        averageOfSubjectDTO1.setSubjectName("Git");
        averageOfSubjectDTO1.setAverage(scoreGit);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO1);

        AverageOfSubjectDTO averageOfSubjectDTO2 = new AverageOfSubjectDTO();
        averageOfSubjectDTO2.setSubjectName("React");
        averageOfSubjectDTO2.setAverage(scoreReact);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO2);

        AverageOfSubjectDTO averageOfSubjectDTO3 = new AverageOfSubjectDTO();
        averageOfSubjectDTO3.setSubjectName("Spring Boot");
        averageOfSubjectDTO3.setAverage(scoreSpring);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO3);

        AverageOfSubjectDTO averageOfSubjectDTO4 = new AverageOfSubjectDTO();
        averageOfSubjectDTO4.setSubjectName("CTDL");
        averageOfSubjectDTO4.setAverage(scoreCTDL);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO4);

        AverageOfSubjectDTO averageOfSubjectDTO5 = new AverageOfSubjectDTO();
        averageOfSubjectDTO5.setSubjectName("Giải thuật");
        averageOfSubjectDTO5.setAverage(scoreGiaiThuat);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO5);

        AverageOfSubjectDTO averageOfSubjectDTO6 = new AverageOfSubjectDTO();
        averageOfSubjectDTO6.setSubjectName("Scrum");
        averageOfSubjectDTO6.setAverage(scoreScrum);
        listAverageOfSubjectDTO.add(averageOfSubjectDTO6);

        trainingStatsMaxDTO.setAverageOfSubject(listAverageOfSubjectDTO);
        return trainingStatsMaxDTO;
    }
}
