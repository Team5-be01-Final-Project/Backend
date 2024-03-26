package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.dto.AuthorityCodeDTO;
import com.sales.BPS.msystem.repository.AuthorityCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorityCodeService {
    private final AuthorityCodeRepository authorityCodeRepository;

    @Autowired
    public AuthorityCodeService(AuthorityCodeRepository authorityCodeRepository) {
        this.authorityCodeRepository = authorityCodeRepository;
    }
    public List<AuthorityCodeDTO> findAllAuthorityCodes() {
        return authorityCodeRepository.findAllAuthorityCodesAsDTO();
    }
}
