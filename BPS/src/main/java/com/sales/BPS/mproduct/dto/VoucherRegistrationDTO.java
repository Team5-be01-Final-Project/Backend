package com.sales.BPS.mproduct.dto;


import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class VoucherRegistrationDTO {

    private Long voucId;
    private LocalDate voucDate;
    private Integer empCode; // 담당자 코드 추가
    private String signerName;
    private String clientCode; // 거래처 코드 추가
    private String clientName;
    private String storageCar;
    private List<VoucherItemDto> items = new ArrayList<>();

}

