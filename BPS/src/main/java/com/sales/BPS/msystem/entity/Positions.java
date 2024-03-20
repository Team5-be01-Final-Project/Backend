package com.sales.BPS.msystem.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Positions {

    @Id
    @Column(name = "pos_code")
    private String posCode;

    @Column(name = "pos_name")
    private String posName;

    @OneToMany(mappedBy = "positions")
    private List<Employee> employees = new ArrayList<>();

}

