package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Lesson;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface LessonService {
    public List<Lesson> getAllLesson(int subjectId);
    public Optional<Lesson> getLessonById(int id);
    Page<LessonAdminDTO> getByPagingRequest(PagingRequest pagingRequest);
    void addLesson(Lesson lesson);
    Optional<Lesson> getLessonAdminDTOById(int id);




}
