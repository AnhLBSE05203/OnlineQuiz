package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.model.Course;
import com.fpt.OnlineQuiz.model.Subject;

import java.util.List;

public interface SubjectService {
    List<Subject>  getAllMySubject(int account_id);

    List<Subject> getNext3Subject(int account_id, int amount);

    List<Subject> getFeaturedSubjects(int number);

    List<Subject> findAllSubjects();

    List<SubjectAdminDTO> getAllSubjectAdminDTO();

    SubjectAdminDTO getSubjectAdminDTOById(int id);

    Subject getSubjectById(int id);

    void update(Subject subject);
}
