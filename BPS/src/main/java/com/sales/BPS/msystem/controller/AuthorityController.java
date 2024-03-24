package com.sales.BPS.msystem.controller;


import com.sales.BPS.msystem.dto.AuthorityCodeDTO;
import com.sales.BPS.msystem.dto.AuthorityUpdateRequestDTO;
import com.sales.BPS.msystem.service.AuthorityCodeService;
import com.sales.BPS.msystem.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    private final AuthorityCodeService authorityCodeService;

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityCodeService authorityCodeService, AuthorityService authorityService) {
        this.authorityCodeService = authorityCodeService;
        this.authorityService = authorityService;
    }

    @GetMapping("/codes")
    public List<AuthorityCodeDTO> getAllAuthorityCodes() {
        return authorityCodeService.findAllAuthorityCodes();
    }

    @PostMapping("/changeAuthority")
    public ResponseEntity<?> changeAuthority(@RequestBody AuthorityUpdateRequestDTO request) {
        try {
            authorityService.updateAuthority(request.getEmpCode(), request.getAuthCode(), request.getNewAuthCode());
            return ResponseEntity.ok().body("Authority updated successfully.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error updating authority: " + ex.getMessage());
        }
    }
}

