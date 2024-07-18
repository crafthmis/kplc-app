package com.kplc.bulksms.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="tbl_area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="area_id")
    private Long areaId;
    @Column(name="cst_id")
    private Long cstId;
    private String name;
    @Column(name="long")
    private String lon;
    private String lat;
    @ManyToOne
    @JoinColumn(name="cst_id" , referencedColumnName = "cst_id",nullable=false ,insertable=false, updatable=false)
    Constituency constituency;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "area", cascade = CascadeType.ALL)
    private List<Contact> contacts;
    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
