package com.sales.BPS.msystem.controller;


import com.sales.BPS.msystem.dto.AuthorityCodeDTO;
import com.sales.BPS.msystem.service.AuthorityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authorities")
public class AuthorityController {
    private final AuthorityCodeService authorityCodeService;

    @Autowired
    public AuthorityController(AuthorityCodeService authorityCodeService) {
        this.authorityCodeService = authorityCodeService;
    }

    @GetMapping("/codes")
    public List<AuthorityCodeDTO> getAllAuthorityCodes() {
        return authorityCodeService.findAllAuthorityCodes();
    }
}

