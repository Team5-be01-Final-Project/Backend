package com.sales.BPS.msystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class EmployeeInfoDTO {
    private Integer empCode;
    private String empName;
    private String posName; // 직급 이름
    private String deptName; // 부서 이름
    private String empTel;
    private String authName; // 권한 이름


    public EmployeeInfoDTO(Integer empCode, String empName, String posName, String deptName, String empTel, String authName) {
        this.empCode = empCode;
        this.empName = empName.replace("\r", "");
        this.deptName = deptName.replace("\r", "");
        this.posName = posName.replace("\r", "");
        this.empTel = empTel.replace("\r", "");
        this.authName = authName.replace("\r", "");
    }
}




