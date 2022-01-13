package com.fpt.OnlineQuiz.service;

import com.fpt.OnlineQuiz.model.Role;

import java.util.List;

public interface RoleService {
    /**
     * Retrieve all Roles from DB
     * @return
     */
    List<Role> findAll();
}
