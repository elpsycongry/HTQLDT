package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.dto.InternDTO;
import com.example.quanlydaotao.dto.InternScoreDTO;
import com.example.quanlydaotao.dto.InternSubjectDTO;
import com.example.quanlydaotao.model.*;
import com.example.quanlydaotao.repository.InternProfileRepository;
import com.example.quanlydaotao.repository.InternScoreRepository;
import com.example.quanlydaotao.repository.InternSubjectRepository;
import com.example.quanlydaotao.service.InternService;
import com.example.quanlydaotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Service
public class InternServiceImpl implements InternService {
    @Autowired
    private InternProfileRepository internProfileRepository;

    @Autowired
    private InternScoreRepository internScoreRepository;

    @Autowired
    private InternSubjectRepository internSubjectRepository;
    @Autowired
    private UserService userService;

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
    public List<InternSubject> getSubjects() {
        return internSubjectRepository.findAll();
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
            //Khai báo danh sách điểm 1 thực tập sinh
            List<InternScoreDTO> internScoreDTOList = new ArrayList<>();
            //Khai báo danh sách môn học và điểm lí thuyết, thực hành, thái độ
            List<InternSubjectDTO> internSubjectDTOList = new ArrayList<>();
            //Khai báo điểm trung bình chung cuối khoá học
            double finalScore = 0;

//            Lấy ra tên các môn học cho vào List<InternSubjectDTO>
            for (int j = 0; j < internSubjects.size(); j++) {
                InternSubjectDTO internSubjectDTO = new InternSubjectDTO((long) (j+1), internSubjects.get(j).getName());
                internSubjectDTOList.add(j, internSubjectDTO);
            }

            //Lấy ra danh sách điểm tất cả môn học và loại điểm của thực tâp cho vào internScoreDTOList
            for (int j = 0; j < internScores.size(); j++) {
                InternScore internScore = internScores.get(j);
                InternScoreDTO internScoreDTO = new InternScoreDTO(
                        internScore.getId(),
                        internScore.getValue(),
                        internScore.getType(),
                        internScore.getInternSubject()
                );
                internScoreDTOList.add(j, internScoreDTO);
            }

            //Duyệt qua danh sách điểm
            for (int j = 0; j < internScoreDTOList.size(); j++) {
                //Lấy ra 3 giá trị từng đối tượng trong mảng internScoreDTOList
                String nameSubject = internScoreDTOList.get(j).getInternSubject().getName();
                String type = internScoreDTOList.get(j).getType();
                String value = internScoreDTOList.get(j).getValue();

                //Tìm vị trí môn học theo tên trong danh sách internScoreDTOList
                int indexSubject = IntStream.range(0, internSubjectDTOList.size())
                        .filter(k -> internSubjectDTOList.get(k).getNameSubject().equals(nameSubject))
                        .findFirst().getAsInt();

                //Lấy ra đối tượng môn học internSubjectDTO
                InternSubjectDTO internSubjectDTO = internSubjectDTOList.get(indexSubject);
                if (type.equals("theory")) {
                    internSubjectDTO.setTheoryScore(value);
                }
                if (type.equals("practice")) {
                    internSubjectDTO.setPracticeScore(value);
                }
                if (type.equals("attitude")) {
                    internSubjectDTO.setAttitudeScore(value);
                }
                internSubjectDTOList.set(indexSubject, internSubjectDTO);
            }


            for (int j = 0; j < internSubjectDTOList.size(); j++) {
                InternSubjectDTO internSubjectDTO = internSubjectDTOList.get(j);
                double theoryScore = Double.parseDouble(internSubjectDTO.getTheoryScore());
                double practice = Double.parseDouble(internSubjectDTO.getPracticeScore());
                double attitude = Double.parseDouble(internSubjectDTO.getAttitudeScore());
                double totalScore = Math.round(((theoryScore + (practice + attitude) * 2) / 5) * 100.0) / 100.0;
                finalScore = finalScore + totalScore;
                internSubjectDTO.setTotalScore(totalScore);
                internSubjectDTOList.set(j, internSubjectDTO);
            }

            finalScore = Math.round((finalScore / internSubjectDTOList.size()) * 100.0) / 100.0;

            InternDTO internDTO = new InternDTO(
                    internProfile.getUser().getId(),
                    internProfile.getUser().getName(),
                    internProfile.getStartDate(),
                    internProfile.getEndDate(),
                    internProfile.getTrainingState(),
                    internProfile.getIsPass(),
                    finalScore,
                    internProfile.getScoreInTeam(),
                    internSubjectDTOList);
            internDTOList.add(i, internDTO);
        }
        return internDTOList;
    }
    @Override
    public Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), internDTOList.size());

        return new PageImpl<>(internDTOList.subList(start, end), pageable, internDTOList.size());
    }
}