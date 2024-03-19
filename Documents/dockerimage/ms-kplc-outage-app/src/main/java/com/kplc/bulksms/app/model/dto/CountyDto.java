package com.kplc.bulksms.app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CountyDto {

    private Long cntId;
    private String code;
    private String name;
}
