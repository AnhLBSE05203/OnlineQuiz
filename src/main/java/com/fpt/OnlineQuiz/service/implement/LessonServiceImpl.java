package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.CRUDRepository.CRUDLessonRepository;
import com.fpt.OnlineQuiz.dao.LessonRepository;
import com.fpt.OnlineQuiz.dto.LessonAdminDTO;
import com.fpt.OnlineQuiz.dto.SubjectAdminDTO;
import com.fpt.OnlineQuiz.dto.paging.Page;
import com.fpt.OnlineQuiz.dto.paging.PagingRequest;
import com.fpt.OnlineQuiz.model.Lesson;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.LessonService;
import com.fpt.OnlineQuiz.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    @Autowired
    private CRUDLessonRepository CRUDLessonRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Override
    public List<Lesson> getAllLesson(int subjectId) {
        return (List<Lesson>) CRUDLessonRepository.findBySubId(subjectId);
    }

    @Override
    public Optional<Lesson> getLessonById(int id) {
        return CRUDLessonRepository.findById(id);
    }

    @Override
    public Page<LessonAdminDTO> getByPagingRequest(PagingRequest pagingRequest) {
        List<Lesson> lessons = lessonRepository.getByPagingRequest(pagingRequest);
        long count = lessonRepository.getLessonCountByPagingRequest(pagingRequest);
        List<LessonAdminDTO> LessonAdminDTO = new ArrayList<>();
        // convert Subject to SubjectAdminDTO
        for (Lesson lesson : lessons) {
            LessonAdminDTO lessonAdminDTO = lesson.toLessonAdminDTO();
            LessonAdminDTO.add(lessonAdminDTO);
        }
        // convert List to Page
        Page<LessonAdminDTO> page = new Page<>(LessonAdminDTO);
        page.setRecordsFiltered((int) count);
        page.setRecordsTotal((int) count);
        page.setDraw(pagingRequest.getDraw());

        return page;
    }
    public Lesson getLessonByLessonId(int id){
        return lessonRepository.getLessonByLessonId(id);
    }

    @Override
    public List<Lesson> getLessonByCourseId(int courseId) {
        return CRUDLessonRepository.findByCourseId(courseId);
    }

    @Override
    public List<Lesson> getAll() {
        return (List<Lesson>) CRUDLessonRepository.findAll();
    }

    @Override
    public void addLesson(Lesson lesson) {
        lessonRepository.addLesson(lesson);
    }

    @Override
    public LessonAdminDTO getLessonAdminDTOById(int id) {
        Optional<Lesson> lesson = CRUDLessonRepository.findById(id);
        return lesson.get().toLessonAdminDTO();
    }

    @Override
    public void updateLesson(Lesson lesson) {
        lessonRepository.updateLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        CRUDLessonRepository.delete(lesson);
    }
}
