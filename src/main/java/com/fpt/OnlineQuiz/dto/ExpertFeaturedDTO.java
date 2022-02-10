package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpertFeaturedDTO {
    private int accountId;
    private String fullName;
    private String description;
    private String imgSrc;
}
