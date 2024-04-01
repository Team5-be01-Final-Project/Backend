package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {
    Optional<Authority> findByEmpCode(Integer empCode);

    @Query("SELECT a FROM Authority a WHERE a.authCode = 'AUTH003'")
    List<Authority> findEmployeesWithAuthCode(String auth003);
}
