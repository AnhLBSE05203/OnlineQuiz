package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;
import com.fpt.OnlineQuiz.model.Expert;

import java.util.List;

public interface ExpertService {
    List<ExpertFeaturedDTO> getTopExperts(int number);
}
