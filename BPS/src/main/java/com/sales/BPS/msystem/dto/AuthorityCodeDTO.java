package com.sales.BPS.msystem.dto;

import lombok.Data;

@Data
public class AuthorityCodeDTO {
    private String authCode;
    private String authName;

    public AuthorityCodeDTO(String authCode, String authName) {
        this.authCode = authCode.replace("\r","");
        this.authName = authName.replace("\r","");
    }
}
