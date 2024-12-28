package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.exception.EntityNotFoundException;
import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getUserRole(){
        return roleRepository
                .findByName("ROLE_USER")
                .orElseThrow(()-> new EntityNotFoundException("Role does not exist"));
    }

}
