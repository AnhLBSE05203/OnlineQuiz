package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.dto.ExpertFeaturedDTO;

import java.util.List;

public interface ExpertService {
    List<ExpertFeaturedDTO> getFeaturedExperts(int number);
}
