package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogAdminAddDTO {
    private Integer id;
    private String title;
    private String content2;
    private String statusStr;
    private Integer status;
}
