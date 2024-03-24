package com.sales.BPS.msystem.repository;

import com.sales.BPS.msystem.entity.Authority;
import com.sales.BPS.msystem.entity.AuthorityPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, AuthorityPK> {

}
