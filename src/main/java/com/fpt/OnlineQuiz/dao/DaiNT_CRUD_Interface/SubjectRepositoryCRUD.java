package com.fpt.OnlineQuiz.dao.DaiNT_CRUD_Interface;

import com.fpt.OnlineQuiz.model.Subject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepositoryCRUD extends CrudRepository<Subject,Integer> {

}
