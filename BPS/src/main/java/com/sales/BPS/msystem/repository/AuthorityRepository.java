package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {
    Optional<Authority> findByEmpCode(Integer empCode);

    @Query("SELECT a.authorityCode.authName FROM Authority a WHERE a.empCode = :empCode")
    String findAuthNameByEmpCode(@Param("empCode") Integer empCode);
}
