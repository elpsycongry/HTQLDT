package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.InternSubjectDTO;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private SubjectCommentRepo subjectCommentRepo;

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
    public InternProfile getInternProfile(Long userID) {
        return internProfileRepository.findById(userID).get();
    }

    @Override
    public List<InternScore> getInternScore(User user) {
        return internScoreRepository.getInternScoresByUser(user);
    }


    @Override
    public List<InternSubject> getSubjects() {
        return internSubjectRepository.findAll();
    }

    @Override
    public List<Object[]> getAllByUserId(Long id) {
        return internScoreRepository.getAllByUserId(id);
    }

    @Override
    public InternProfile save(InternProfile internProfile) {
        return internProfileRepository.save(internProfile);
    }

    @Override
    public List<InternScore> getListInternScoreByUserID(Long userID) {
        return internScoreRepository.getAllByUser(userRepository.findById(userID).get());
    }

    @Override
    public InternSubject findInternSubjectByName(String name) {
        return internSubjectRepository.findByName(name).get();
    }

    @Override
    public Optional<InternScore> getInternScoreByUserAndSubjectAndType(User user, InternSubject subject,String type) {
        return internScoreRepository.findByUserAndInternSubjectAndType(user , subject, type);
    }

    @Override
    public Optional<InternProfile> getInternProfileByUserID(Long userId) {

        return internProfileRepository.findByUser(userRepository.findById(userId).get());
    }

    @Override
    public void saveInternScore(InternScore internScore) {
         internScoreRepository.save(internScore);
    }

    @Override
    public List<SubjectComment> getListSubjectCommentByUserID(Long userID) {
        return subjectCommentRepo.findAllByUser(userRepository.findById(userID).get());
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
            List<InternScore> internScores = internScoreRepository.findAllByUser(internProfile.getUser());
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

            InternDTO internDTO = new InternDTO(
                    internProfile.getUser().getId(),
                    internProfile.getUser().getName(),
                    internProfile.getStartDate(),
                    internProfile.getEndDate(),
                    internProfile.getTrainingState(),
                    (checkFinalScore ? String.valueOf(finalScore) : "NA"),
                    internProfile.getScoreInTeam(),
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
    public Iterable<InternDTO> findListInterWithNameInter(String keyword, Iterable<InternDTO> internDTOIterable) {
        List<InternDTO> internDTOList = (List<InternDTO>) internDTOIterable;
        List<InternDTO> matchingInternDTO = internDTOList.stream()
                .filter(f -> f.getUserName().toLowerCase().contains(String.valueOf(keyword).toLowerCase()))
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
    public Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), internDTOList.size());

        return new PageImpl<>(internDTOList.subList(start, end), pageable, internDTOList.size());
    }
}