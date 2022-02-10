package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.SubjectRepository;
import com.fpt.OnlineQuiz.model.Subject;
import com.fpt.OnlineQuiz.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SubjectServiceImpl implements SubjectService {

    @Autowired
    private SubjectRepository repository;

    @Override
    public List<Subject> getAllMySubject(int account_id) {
        List<Integer> list_package_id = repository.getPackageId(account_id);
        List<Subject> list_subject = new ArrayList<>();
        for (int i = 0; i < list_package_id.size(); i++){
            List<Subject> list = repository.getSubjectByPackageId(list_package_id.get(i));
            for (int j = 0; j < list.size(); j++){
                list_subject.add(list.get(j));
            }
        }
        return list_subject;
    }
}
