package com.fpt.OnlineQuiz.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountAdminDTO {
    private String fullName;
    private String phone;
    private String email;
    private String password;
    private int gender;
    private String role;
}
