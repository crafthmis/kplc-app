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
@Table(name="tbl_county")
public class County {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cty_id")
    private Long cntId;
    @Column(name="reg_id")
    private Long regId;
    private String code;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reg_id", referencedColumnName = "reg_id", insertable=false, updatable=false)
    private  Region region;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "county", cascade = CascadeType.ALL)
    private List<Constituency> constituencies;

    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
