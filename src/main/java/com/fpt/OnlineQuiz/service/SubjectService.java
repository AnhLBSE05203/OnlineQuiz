package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> getAllMySubject(int account_id);

    List<Subject> getNext3Subject(int account_id, int amount);

    List<Subject> getFeaturedSubjects(int number);

    Optional<Subject> getSubject(int id);
    List<Subject> findAllSubjects();

    List<SubjectAdminDTO> getAllSubjectAdminDTO();

    SubjectAdminDTO getSubjectAdminDTOById(int id);

    Subject getSubjectById(int id);

    void update(Subject subject);

}
