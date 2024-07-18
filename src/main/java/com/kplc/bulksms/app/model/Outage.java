package com.kplc.bulksms.app.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="tbl_outage")
public class Outage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ots_id")
    private Long otsId;
    private String message;
    @Column(name="outage_date")
    private Date outageDate;
    @Column(name="send_status")
    private String sendStatus;
    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
