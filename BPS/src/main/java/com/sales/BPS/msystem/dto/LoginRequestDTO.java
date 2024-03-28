package com.sales.BPS.msystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class LoginRequestDTO {
    private Integer empCode;
    private String empPw;
}
