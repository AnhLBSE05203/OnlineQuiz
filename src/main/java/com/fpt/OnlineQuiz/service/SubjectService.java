package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Subject;

import java.util.List;
import java.util.Optional;

public interface SubjectService {
    List<Subject> findAllSubject();

    List<Subject> getAllSubject(int account_id);

    List<Subject> getNext3Subject(int account_id, int amount);

    List<Subject> getFeaturedSubjects(int number);

    Optional<Subject> getSubject(int id);

    List<Subject> findAllSubjects();

    List<SubjectAdminDTO> getAllSubjectAdminDTO();

    Page<SubjectAdminDTO> getByPagingRequest(PagingRequest pagingRequest);

    SubjectAdminDTO getSubjectAdminDTOById(int id);

    Subject getSubjectById(int id);

    void updateSubject(Subject subject);

    void addSubject(Subject subject);

    List<Subject> findAllSubjectsByPaging(int pageindex, int pageSize);

   Subject findSubByName(String name);
}
