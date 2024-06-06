package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.InternSubjectDTO;
import com.example.quanlydaotao.dto.RecruitmentPlanDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.model.InternProfile;
import com.example.quanlydaotao.model.InternScore;
import com.example.quanlydaotao.model.InternSubject;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.*;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class InternServiceImpl implements InternService {
    private static final Logger log = LoggerFactory.getLogger(InternServiceImpl.class);
    @Autowired
    private InternProfileRepository internProfileRepository;

    @Autowired
    private InternScoreRepository internScoreRepository;

    @Autowired
    private InternSubjectRepository internSubjectRepository;

    @Autowired
    private IInternRepository iInternRepository;

    @Autowired
    private SubjectCommentRepo subjectCommentRepo;

    @Autowired
    private RecruitmentPlanDetailService recruitmentPlanDetailService;

    @Autowired
    private IRecruitmentPlanRepository recruitmentPlanRepository;

    @Autowired
    private IRecruitmentRequestRepository recruitmentRequestRepository;

    @Override
    public List<InternProfile> getListIntern() {
        List<InternProfile> internProfiles = internProfileRepository.findAll();
        return internProfiles;
    }

    @Override
    public List<InternScore> getListInternScore() {
        return internScoreRepository.findAll();
    }

    @Override
    public InternProfile getInternProfile(Long internID) {
        return internProfileRepository.findById(internID).get();
    }

    @Override
    public List<InternScore> getInternScore(Intern intern) {
        return internScoreRepository.getInternScoreByIntern(intern);
    }


    @Override
    public List<InternSubject> getSubjects() {
        return internSubjectRepository.findAll();
    }

    @Override
    public List<Object[]> getAllByInternId(Long id) {
        return internScoreRepository.getAllByInternId(id);
    }

    @Override
    public InternProfile save(InternProfile internProfile) {
        return internProfileRepository.save(internProfile);
    }

    @Override
    public List<InternScore> getListInternScoreByInternID(Long internID) {
        return internScoreRepository.getAllByIntern(iInternRepository.findById(internID).get());
    }

    @Override
    public InternSubject findInternSubjectByName(String name) {
        return internSubjectRepository.findByName(name).get();
    }

    @Override
    public Optional<InternScore> getInternScoreByInternAndSubjectAndType(Intern intern, InternSubject subject,String type) {
        return internScoreRepository.findByInternAndInternSubjectAndType(intern , subject, type);
    }

    @Override
    public Optional<InternProfile> getInternProfileByInternID(Long internID) {

        return internProfileRepository.findInternProfileByIntern(iInternRepository.findById(internID).get());
    }

    @Override
    public void saveInternScore(InternScore internScore) {
        internScoreRepository.save(internScore);
    }

    @Override
    public List<SubjectComment> getListSubjectCommentByInternID(Long internID) {
        return subjectCommentRepo.findAllByIntern(iInternRepository.findById(internID).get());
    }

    @Override
    public Optional<SubjectComment> getSubjectCommentByID(Long idComment) {
        return subjectCommentRepo.findById(idComment);
    }

    @Override
    public void saveSubjectComment(SubjectComment subjectComment) {
        subjectCommentRepo.save(subjectComment);
    }

    @Override
    public Optional<Intern> findById(Long internID) {
        return iInternRepository.findById(internID) ;
    }

    @Override
    public String isFullIntern(long recruitmentPlanId) {
        int totalInternNeed = recruitmentPlanDetailService.getTotalResult(recruitmentPlanId);
        int applicants = 0;
        for (InternProfile internProfile : internProfileRepository.findAll()){
            if (internProfile.getIsPass() != null && internProfile.getIntern().getRecruitmentPlan().getId() == recruitmentPlanId && internProfile.getIsPass()){
                applicants++;
            }
        }
        if (applicants == totalInternNeed) {
            return "enough";
        }
        if (applicants < totalInternNeed) {
            return "notEnough";
        }
        if (applicants > totalInternNeed) {
            return "tooMany";
        }
        return null;
    }

    @Override
    public void checkNumberOfRecruitment() {
        List<InternProfile> profileList = getListIntern();
        for (InternProfile profile : profileList) {
            if (profile.getIsPass() != null  && profile.getIsPass() == true){
                if (isFullIntern(profile.getIntern().getRecruitmentPlan().getId()) == "enough"){
                    profile.getIntern().getRecruitmentPlan().setStatus("Đã hoàn thành");
                    recruitmentPlanRepository.save(profile.getIntern().getRecruitmentPlan());

                    profile.getIntern().getRecruitmentPlan().getRecruitmentRequest().setStatus("Đã bàn giao");
                    recruitmentRequestRepository.save(profile.getIntern().getRecruitmentPlan().getRecruitmentRequest());
                }
                if (isFullIntern(profile.getIntern().getRecruitmentPlan().getId()) == "notEnough"){
                    profile.getIntern().getRecruitmentPlan().setStatus("Đã xác nhận");
                    recruitmentPlanRepository.save(profile.getIntern().getRecruitmentPlan());

                    profile.getIntern().getRecruitmentPlan().getRecruitmentRequest().setStatus("Đang tuyển dụng");
                    recruitmentRequestRepository.save(profile.getIntern().getRecruitmentPlan().getRecruitmentRequest());
                }
            }
            if (profile.getIsPass() == null || profile.getIsPass() == false) {
                if (isFullIntern(profile.getIntern().getRecruitmentPlan().getId()) == "enough"){
                    profile.setIsPass(false);
                    profile.setTrainingState("trained");
                    profile.setEndDate(LocalDate.now());
                    save(profile);
                }
            }
        }
    }


    @Override
    public Iterable<InternDTO> getAllInter() {

        //Tạo mới danh sách chứa các thông tin thực tập sinh
        List<InternDTO> internDTOList = new ArrayList<>();

        //Lấy ra danh sách thực tập sinh và thông tin của họ
        List<InternProfile> internProfiles = internProfileRepository.findAll();

        //Lấy ra danh sách môn học
        List<InternSubject> internSubjects = getSubjects();

        for (int i = 0; i < internProfiles.size(); i++) {
            //Lấy ra thông tin TTS
            InternProfile internProfile = internProfiles.get(i);
            //Lấy ra danh sách điểm InterScore của 1 thực tập sinh
            List<InternScore> internScores = internScoreRepository.findAllByIntern(internProfile.getIntern());
            //Khai báo danh sách môn học và điểm lí thuyết, thực hành, thái độ
            List<InternSubjectDTO> internSubjectDTOList = new ArrayList<>();            //Khai báo điểm trung bình chung cuối khoá học
            boolean checkFinalScore = true;
            double finalScore = 0;

            for (int j = 0; j < internSubjects.size(); j++) {
                Long id = (long) (j + 1);
                InternSubjectDTO internSubjectDTO = new InternSubjectDTO(id, internSubjects.get(j).getName());
                internSubjectDTOList.add(j, internSubjectDTO);
            }

            //Duyệt qua danh sách điểm với 27 con điểm cho 7 môn và 3 loại
            for (int j = 0; j < internScores.size(); j++) {

                //Lấy ra 3 giá trị từng đối tượng trong bảng điểm của 1 intern
                String nameSubject = internScores.get(j).getInternSubject().getName();
                String type = internScores.get(j).getType();
                String value = internScores.get(j).getValue();

                //Tìm vị trí môn học theo tên trong danh sách internScoreDTOList
                int indexSubject = IntStream.range(0, internSubjectDTOList.size())
                        .filter(k -> internSubjectDTOList.get(k).getNameSubject().equals(nameSubject))
                        .findFirst().getAsInt();

                //Lấy ra đối tượng môn học internSubjectDTO
                InternSubjectDTO internSubjectDTO = internSubjectDTOList.get(indexSubject);

                //Gán giá trị từng loại điểm cho môn học
                if (type.equals("theory")) {
                    if (value == null || value.isEmpty()) {
                        internSubjectDTO.setTheoryScore("NA");
                        internSubjectDTO.setTotalScore("NA");
                    } else {
                        internSubjectDTO.setTheoryScore(value);
                    }
                }
                if (type.equals("practice")) {
                    if (value == null || value.isEmpty()) {
                        internSubjectDTO.setPracticeScore("NA");
                        internSubjectDTO.setTotalScore("NA");
                    } else {
                        internSubjectDTO.setPracticeScore(value);
                    }
                }
                if (type.equals("attitude")) {
                    if (value == null || value.isEmpty()) {
                        internSubjectDTO.setAttitudeScore("NA");
                    } else {
                        internSubjectDTO.setAttitudeScore(value);
                        internSubjectDTO.setTotalScore("NA");
                    }
                }
                internSubjectDTOList.set(indexSubject, internSubjectDTO);
            }

//          Tính điểm trung bình môn
            for (int j = 0; j < internSubjectDTOList.size(); j++) {
                InternSubjectDTO internSubjectDTO = internSubjectDTOList.get(j);
                String theoryScore = internSubjectDTO.getTheoryScore();
                String practiceScore = internSubjectDTO.getPracticeScore();
                String attitudeScore = internSubjectDTO.getAttitudeScore();
                if (theoryScore != "NA" && practiceScore != "NA" && attitudeScore != "NA") {
                    double totalScore = Math.round(((
                            Double.parseDouble(theoryScore) + (Double.parseDouble(practiceScore) + Double.parseDouble(attitudeScore)) * 2) / 5) * 100.0) / 100.0;
                    internSubjectDTO.setTotalScore(String.valueOf(totalScore));
                    internSubjectDTOList.set(j, internSubjectDTO);
                }
            }
            int count = 0;
            for (int j = 0; j < internSubjectDTOList.size(); j++) {
                InternSubjectDTO internSubjectDTO = internSubjectDTOList.get(j);
                if (internSubjectDTO.getTotalScore() != "NA") {
                    count ++;
                    finalScore = finalScore + Double.parseDouble(internSubjectDTO.getTotalScore());
                }
            }
            if (count == 0) {
                checkFinalScore = false;
            } else {
                finalScore = Math.round((finalScore / count) * 100.0) / 100.0;
            }

            RecruitmentPlanDTO recruitmentPlanDTO = getRecruitmentPlanDTO(  internProfiles.get(i).getIntern().getRecruitmentPlan());

            InternDTO internDTO = new InternDTO(
                    internProfile.getIntern().getId(),
                    internProfile.getIntern().getName(),
                    internProfile.getStartDate(),
                    internProfile.getEndDate(),
                    internProfile.getTrainingState(),
                    internProfile.getIsPass(),
                    (checkFinalScore ? String.valueOf(finalScore) : "NA"),
                    internProfile.getScoreInTeam(),
                    recruitmentPlanDTO,
                    internSubjectDTOList);
            internDTOList.add(i, internDTO);
        }
        return internDTOList;
    }

    @Override
    public Iterable<InternDTO> findListInterWithNameInternAndTrainingState(String keyword, String trainingState) {
        Iterable<InternDTO> internDTOIterable = getAllInter();

        if (!keyword.isEmpty() && !trainingState.isEmpty()) {
            internDTOIterable = findListInterWithNameInter(keyword, internDTOIterable);
            internDTOIterable = findListInterWithTrainingState(trainingState, internDTOIterable);
            return internDTOIterable;
        }
        if (keyword.isEmpty() && !trainingState.isEmpty()) {
            return findListInterWithTrainingState(trainingState, internDTOIterable);
        }

        if (!keyword.isEmpty() && trainingState.isEmpty()) {
            return findListInterWithNameInter(keyword, internDTOIterable);
        }
        return internDTOIterable;
    }
    @Override
    public Iterable<InternDTO> findListInterWithNameInternAndTrainingStateAndRecruitmentPlan(String keyword, String trainingState, String recruitmentPlan) {
        Iterable<InternDTO> internDTOIterable = getAllInter();

        boolean hasKeyword = !keyword.isEmpty();
        boolean hasTrainingState = !trainingState.isEmpty();
        boolean hasRecruitmentPlanId = recruitmentPlan != null && !recruitmentPlan.isEmpty();

        if (hasKeyword && hasTrainingState && hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithNameInter(keyword, internDTOIterable);
            internDTOIterable = findListInterWithTrainingState(trainingState, internDTOIterable);
            internDTOIterable = findListInterWithRecruitmentPlan(recruitmentPlan, internDTOIterable);
            return internDTOIterable;
        } else if (!hasKeyword && hasTrainingState && hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithTrainingState(trainingState, internDTOIterable);
            internDTOIterable = findListInterWithRecruitmentPlan(recruitmentPlan, internDTOIterable);
            return internDTOIterable;
        } else if (hasKeyword && !hasTrainingState && hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithNameInter(keyword, internDTOIterable);
            internDTOIterable = findListInterWithRecruitmentPlan(recruitmentPlan, internDTOIterable);
            return internDTOIterable;
        } else if (!hasKeyword && !hasTrainingState && hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithRecruitmentPlan(recruitmentPlan, internDTOIterable);
            return internDTOIterable;
        } else if (hasKeyword && hasTrainingState && !hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithNameInter(keyword, internDTOIterable);
            internDTOIterable = findListInterWithTrainingState(trainingState, internDTOIterable);
            return internDTOIterable;
        } else if (hasKeyword && !hasTrainingState && !hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithNameInter(keyword, internDTOIterable);
            return internDTOIterable;
        } else if (!hasKeyword && hasTrainingState && !hasRecruitmentPlanId) {
            internDTOIterable = findListInterWithTrainingState(trainingState, internDTOIterable);
            return internDTOIterable;
        } else {
            return internDTOIterable;
        }
    }
    @Override
    public Iterable<InternDTO> findListInterWithNameInter(String keyword, Iterable<InternDTO> internDTOIterable) {
        List<InternDTO> internDTOList = (List<InternDTO>) internDTOIterable;
        List<InternDTO> matchingInternDTO = internDTOList.stream()
                .filter(f -> f.getInternName().toLowerCase().contains(String.valueOf(keyword).toLowerCase()))
                .collect(Collectors.toList());
        return matchingInternDTO;
    }

    @Override
    public Iterable<InternDTO> findListInterWithTrainingState(String trainingState, Iterable<InternDTO> internDTOIterable) {
        List<InternDTO> internDTOList = (List<InternDTO>) internDTOIterable;
        List<InternDTO> matchingInternDTO = internDTOList.stream()
                .filter(f -> f.getTrainingState().equals(trainingState))
                .collect(Collectors.toList());
        return matchingInternDTO;
    }


    @Override
    public Iterable<InternDTO> findListInterWithRecruitmentPlan(String recruitmentPlan, Iterable<InternDTO> internDTOIterable) {
        List<InternDTO> internDTOList = (List<InternDTO>) internDTOIterable;
        List<InternDTO> matchingInternDTO = internDTOList.stream()
                .filter(f -> f.getRecruitmentPlanDTO().getName().equals(recruitmentPlan))
                .collect(Collectors.toList());
        return matchingInternDTO;
    }

    @Override
    public Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), internDTOList.size());

        return new PageImpl<>(internDTOList.subList(start, end), pageable, internDTOList.size());
    }

    public RecruitmentPlanDTO getRecruitmentPlanDTO(RecruitmentPlan recruitmentPlan) {
        return new RecruitmentPlanDTO(recruitmentPlan.getId(), recruitmentPlan.getName(), recruitmentPlan.getStatus());
    }

    
}