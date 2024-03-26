package com.sales.BPS.msystem.dto;

import lombok.Data;

@Data
public class AuthorityUpdateRequestDTO {
    private Integer empCode;
    private String authCode;
    private String newAuthCode;
}
