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
@Table(name="tbl_constituency")
public class Constituency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cst_id")
    private Long cstId;
    @Column(name="cty_id")
    private Long ctyId;

    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "constituency", cascade = CascadeType.ALL)
    private List<Area> areas;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cty_id", referencedColumnName = "cty_id", insertable=false, updatable=false)
    private  County county;

    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
