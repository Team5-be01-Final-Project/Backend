package com.sales.BPS.msales.dto;

public class ClientDto {
    public ClientDto(String clientCode, String clientName, Integer clientClass, String clientBoss, String clientWhere, String clientPost, String clientEmp, String clientEmpTel) {
        this.clientCode = clientCode;
        this.clientName = clientName;
        this.clientClass = clientClass;
        this.clientBoss = clientBoss;
        this.clientWhere = clientWhere;
        this.clientPost = clientPost;
        this.clientEmp = clientEmp;
        this.clientEmpTel = clientEmpTel;
    }

    private String clientCode;
    private String clientName;
    private Integer clientClass;
    private String clientBoss;
    private String clientWhere;
    private String clientPost;
    private String clientEmp;
    private String clientEmpTel;

}
