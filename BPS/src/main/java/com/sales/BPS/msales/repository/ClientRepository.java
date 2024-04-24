package com.sales.BPS.msales.repository;

import com.sales.BPS.msales.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client,String> {

    // 거래처 조회 (원하는 컬럼만 조회하기 위해 아래 인터페이스에서 값 불러옴)
    @Query("SELECT c.clientCode AS clientCode, c.clientName AS clientName, c.clientClass AS clientClass, c.clientBoss AS clientBoss, c.clientWhere AS clientWhere, c.clientPost AS clientPost, c.clientEmp AS clientEmp, c.clientEmpTel AS clientEmpTel, e.empName AS empName FROM Client c LEFT JOIN c.employee e")
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
        String getEmpName(); // 담당 사원 이름 추가
    }

    //중복확인
    boolean existsByClientCode(String clientCode);

    // 로그인한 사원의 담당 거래처 목록 조회 메서드
    List<Client> findByEmployeeEmpCode(Integer empCode);

}
