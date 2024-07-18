package com.kplc.bulksms.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name="tbl_outage_area")
public class OutageArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="oct_id")
    private Long octId;

    @Column(name="area_id")
    private Long areaId;

    @Column(name="ots_id")
    private Long otsId;
}
