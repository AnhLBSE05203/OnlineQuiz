package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountAdminDTO {
    private int id;

    private String fullName;

    private int gender;

    private String genderStr;

    private String email;

    private String phone;
}
