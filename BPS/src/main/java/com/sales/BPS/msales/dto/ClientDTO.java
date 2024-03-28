package com.sales.BPS.msales.dto;

import com.sales.BPS.msales.entity.Client;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClientDTO {

        private String clientCode;
        private String clientName;
        private Integer clientClass;
        private String clientBoss;
        private String clientWhere;
        private String clientPost;
        private String clientEmp;
        private String clientEmpTel;
        private LocalDate clientStart;
        private LocalDate clientEnd;
        private String clientNote;
        private Integer empCode; // Employee의 empCode를 저장하는 필드 추가

    
    }

