package com.perscolas.fintracker.servise;

import com.perscolas.fintracker.model.entity.Role;
import com.perscolas.fintracker.repository.RoleRepository;
import com.perscolas.fintracker.servise.interfaces.RoleService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class RoleServiceImplTest {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RoleService roleService;

    private final Role role = Role.builder().name("ROLE_USER").build();

    @BeforeEach
    void setUp() {
        roleRepository.save(role);
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
    }

    @Test
    void testGetUserRole() {
        Role actualRole = roleService.getUserRole();
        assertEquals(role, actualRole);
    }

}