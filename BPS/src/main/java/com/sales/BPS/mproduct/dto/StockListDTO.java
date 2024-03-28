package com.sales.BPS.mproduct.dto;

import lombok.Data;

@Data
public class StockListDTO {
    private Integer proCode;
    private String proName;
    private Integer stoAmo;
    private Integer proUnit;

    public StockListDTO(Integer proCode, String proName, Integer stoAmo, Integer proUnit) {
        this.proCode = proCode;
        this.proName = proName;
        this.stoAmo = stoAmo;
        this.proUnit = proUnit;
    }

}
