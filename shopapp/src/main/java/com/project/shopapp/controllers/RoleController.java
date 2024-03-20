package com.project.shopapp.controllers;

import com.project.shopapp.models.Role;
import com.project.shopapp.services.IRoleService;
import com.project.shopapp.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor

public class RoleController {
    private final IRoleService roleService;
    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @GetMapping("")
    public ResponseEntity<?> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        logger.info("Role get all");
        return ResponseEntity.ok(roles);
    }
}
