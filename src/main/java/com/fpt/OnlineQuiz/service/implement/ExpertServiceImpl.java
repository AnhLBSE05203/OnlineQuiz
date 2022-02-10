package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.ExpertRepository;
import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Expert;
import com.fpt.OnlineQuiz.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertRepository expertRepository;
    @Override
    public List<ExpertFeaturedDTO> getTopExperts(int number) {
        return expertRepository.getTopExperts(number);
    }
}
