package com.sales.BPS.msystem.service;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.AuthorityPK;
import com.sales.BPS.msystem.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

    private final AuthorityRepository authorityRepository;


    @Autowired
    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @Transactional
    public void updateAuthority(Integer empCode, String authCode, String newAuthCode) {
        // 복합 키 인스턴스 생성
        AuthorityPK id = new AuthorityPK(empCode, authCode);
        Authority authority = authorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Authority not found with empCode: " + empCode + " and authCode: " + authCode));

        authority.setAuthCode(newAuthCode);
        authorityRepository.save(authority);
    }
}
