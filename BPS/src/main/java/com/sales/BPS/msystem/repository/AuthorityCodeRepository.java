package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.dto.AuthorityCodeDTO;
import com.sales.BPS.msystem.entity.AuthorityCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface AuthorityCodeRepository extends JpaRepository<AuthorityCode, String> {
    @Query("SELECT new com.sales.BPS.msystem.dto.AuthorityCodeDTO(ac.authCode, ac.authName) FROM AuthorityCode ac")
    List<AuthorityCodeDTO> findAllAuthorityCodesAsDTO();
}
