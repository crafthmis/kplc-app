package com.kplc.bulksms.app.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactDto {
    private Long cntId;
    private String area;
    private String msisdn;
    private String username;
    private String email;

    public ContactDto(Long cntId, String area, String email) {
        this.cntId = cntId;
        this.area = area;
        this.email = email;
    }

    public ContactDto(Long cntId, String area, String msisdn, String username, String email) {
        this.cntId = cntId;
        this.area = area;
        this.msisdn = msisdn;
        this.username = username;
        this.email = email;
    }


}
