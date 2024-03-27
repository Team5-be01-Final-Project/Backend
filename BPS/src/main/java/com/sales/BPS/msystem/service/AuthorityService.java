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
    public void updateAuthority(Integer empCode, String oldAuthCode, String newAuthCode) {
        // 기존 Authority 엔티티 찾기
        AuthorityPK oldId = new AuthorityPK(empCode, oldAuthCode);
        Authority oldAuthority = authorityRepository.findById(oldId)
                .orElseThrow(() -> new RuntimeException("Existing authority not found"));

        // 기존 Authority 엔티티 삭제
        authorityRepository.delete(oldAuthority);

        // 새 Authority 엔티티 생성 및 저장
        Authority newAuthority = new Authority();
        newAuthority.setEmpCode(empCode); // 직접 필드 설정
        newAuthority.setAuthCode(newAuthCode); // 직접 필드 설정

        authorityRepository.save(newAuthority);
    }

    public String findAuthNameByEmpCode(Integer empCode) {
        return authorityRepository.findAuthNameByEmpCode(empCode);
    }

}
