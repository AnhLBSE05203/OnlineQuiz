package com.fpt.OnlineQuiz.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AccountDTO {
    private int id;
    private String email;
    private List<RoleDTO> roles;
}
