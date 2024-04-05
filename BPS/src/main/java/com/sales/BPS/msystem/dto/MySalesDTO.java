package com.sales.BPS.msystem.dto;

import com.sales.BPS.msales.entity.Client;
import com.sales.BPS.msystem.entity.Employee;
import lombok.Data;

import java.util.List;

@Data
public class MySalesDTO {
    private Employee employee;
    private List<Client> clients;
}
