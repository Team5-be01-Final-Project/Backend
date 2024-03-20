package com.sales.BPS.msystem.entitiy;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "POSITION")
public class Position {

    @Id
    @Column(name = "pos_code")
    private String posCode;

    @Column(name = "pos_name")
    private String posName;

}

