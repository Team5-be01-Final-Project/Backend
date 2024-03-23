package com.sales.BPS.msales.repository;

// PpcRepository.java

import com.sales.BPS.msales.entity.Ppc;
import com.sales.BPS.msales.entity.PpcPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PpcRepository extends JpaRepository<Ppc, PpcPK> {
    List<Ppc> findByClientCode(String clientCode);

    boolean existsByClientCodeAndProCode(String clientCode, Integer proCode);

    Ppc findByProCode(Integer proCode);

    Optional<Ppc> findByClientCodeAndProCode(String clientCode, Integer proCode);

}

