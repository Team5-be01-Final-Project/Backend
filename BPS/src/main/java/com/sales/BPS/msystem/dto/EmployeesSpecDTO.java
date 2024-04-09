package com.sales.BPS.msystem.dto;

import lombok.Data;
import java.time.LocalDate;


@Data
//@AllArgsConstructor
public class EmployeesSpecDTO {
    private Integer empCode;
    private String empName;
    private String posName; // 직급 이름
    private String deptName; // 부서 이름
    private String empTel;
    private String empEmail;
    private LocalDate empStartDate;
    private LocalDate empEndDate;
    private String empImg;

    public EmployeesSpecDTO(String empImg, Integer empCode, String empName, String posName, String deptName, String empEmail, String empTel, LocalDate empStartDate, LocalDate empEndDate) {
        this.empImg=empImg;
        this.empCode = empCode;
        this.empName = empName.replace("\r", "");
        this.deptName = deptName.replace("\r", "");
        this.posName = posName.replace("\r", "");
        this.empEmail = empEmail.replace("\r", "");
        this.empTel = empTel;
        this.empStartDate=empStartDate;
        this.empEndDate=empEndDate;

    }
}




