package com.sales.BPS.msales.repository;

import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client,String> {

    @Query("SELECT c.clientCode AS clientCode, c.clientName AS clientName, c.clientClass AS clientClass, c.clientBoss AS clientBoss, c.clientWhere AS clientWhere, c.clientPost AS clientPost, c.clientEmp AS clientEmp, c.clientEmpTel AS clientEmpTel FROM Client c")
    List<ClientProjection> findClientsWithSpecificFields();

    interface ClientProjection {
        String getClientCode();
        String getClientName();
        Integer getClientClass();
        String getClientBoss();
        String getClientWhere();
        String getClientPost();
        String getClientEmp();
        String getClientEmpTel();
    }

    //중복확인
    boolean existsByClientCode(String clientCode);
}
