package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {
    Optional<Authority> findByEmpCode(Integer empCode);
}
