package com.fpt.OnlineQuiz.service.implement;

import com.fpt.OnlineQuiz.dao.RoleRepository;
import com.fpt.OnlineQuiz.model.Role;
import com.fpt.OnlineQuiz.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
