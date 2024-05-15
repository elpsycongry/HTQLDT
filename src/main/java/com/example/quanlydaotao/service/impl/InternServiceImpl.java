package com.example.quanlydaotao.service.impl;

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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
    public Iterable<InternDTO> getAllInterWithNameOrTrainingState() {
        List<InternDTO> internDTOList = new ArrayList<>();
        List<InternProfile> internProfiles = internProfileRepository.findAll();
        for (int i = 0; i < internProfiles.size(); i++) {
            User user = internProfiles.get(i).getUser();
            List<InternScore> internScores = internScoreRepository.findAllByUser(user);
            List<InternScoreDTO> internScoreDTOList = new ArrayList<>();
            InternProfile internProfile = internProfiles.get(i);
            InternProfileDTO internProfileDTO = new InternProfileDTO(
                    internProfile.getId(),
                    internProfile.getStartDate(),
                    internProfile.getEndDate(),
                    internProfile.getTrainingState(),
                    internProfile.getIsPass()
            );
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

            InternDTO internDTO = new InternDTO((long) i + 1, user, internProfileDTO, internScoreDTOList);
            internDTOList.add(i, internDTO);
        }
        System.out.println(internDTOList.size());
        return internDTOList;
    }
    @Override
    public List<InternScore> findAllByUser() {
        List<InternProfile> internProfiles = internProfileRepository.findAll();
        User user = internProfiles.get(2).getUser();
        List<InternScore> internScores = internScoreRepository.findAllByUser(user);
        return internScores;
    }

    public Page<InternDTO> convertToPage(List<InternDTO> internDTOList, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), internDTOList.size());

        return new PageImpl<>(internDTOList.subList(start, end), pageable, internDTOList.size());
    }
}
