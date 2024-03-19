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
@Table(name="tbl_region")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reg_id")
    private Long regId;

    private String name;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "region", cascade = CascadeType.ALL)
    private List<County> counties;
    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
