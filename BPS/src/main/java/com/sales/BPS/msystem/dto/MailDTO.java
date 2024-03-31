package com.sales.BPS.msystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MailDTO {
    private String address;
    private String title;
    private String content;
}
