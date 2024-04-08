package com.sales.BPS.msystem.controller;


import com.sales.BPS.msystem.dto.AuthorityCodeDTO;
import com.sales.BPS.msystem.dto.AuthorityUpdateRequestDTO;
import com.sales.BPS.msystem.service.AuthorityCodeService;
import com.sales.BPS.msystem.service.AuthorityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
@Tag(name = "System API", description = "시스템관리 API입니다.")
public class AuthorityController {
    private final AuthorityCodeService authorityCodeService;

    private final AuthorityService authorityService;

    @Autowired
    public AuthorityController(AuthorityCodeService authorityCodeService, AuthorityService authorityService) {
        this.authorityCodeService = authorityCodeService;
        this.authorityService = authorityService;
    }

    @GetMapping("/codes")
    @Tag(name = "System API")
    @Operation(summary = "권한조회",description = "권한을 조회합니다.")
    public List<AuthorityCodeDTO> getAllAuthorityCodes() {
        return authorityCodeService.findAllAuthorityCodes();
    }

    @PostMapping("/changeAuthority")
    @Tag(name = "System API")
    @Operation(summary = "권한 수정",description = "권한을 수정합니다.")
    public ResponseEntity<?> changeAuthority(@RequestBody AuthorityUpdateRequestDTO request) {
        try {
            authorityService.updateAuthority(request.getEmpCode(), request.getAuthCode(), request.getNewAuthCode());
            return ResponseEntity.ok().body("Authority updated successfully.");
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error updating authority: " + ex.getMessage());
        }
    }
}

