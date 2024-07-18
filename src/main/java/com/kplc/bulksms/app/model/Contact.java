package com.kplc.bulksms.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

/**
 *
 * @author SMWAURA1
 */
@Entity
@Getter
@Setter
@Table(name="tbl_contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cnt_id")
    private Long cntId;
    @Column(name="area_id")
    private Long areaId;
    private String msisdn;
    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;
    @Column(name="username")
    private String userName;
    private String password;
    private String email;
    @Column(name="auth_token")
    private String token;
    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "area_id", referencedColumnName = "area_id", insertable=false, updatable=false)
    private Area area;
}
