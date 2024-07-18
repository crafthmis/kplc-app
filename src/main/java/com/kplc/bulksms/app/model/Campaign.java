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
@Table(name="tbl_campaign")
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cmp_id")
    private Long cmpId;
    @Column(name="ots_id")
    private Long otsId;
    private String msisdn;
    private String message;
    @Column(name="send_status")
    private String sendStatus;
    @Column(name="ack_status")
    private String ackStatus;
    @Column(name="recv_status")
    private String recvStatus;
    @CreationTimestamp
    @Column(name="date_created")
    private Date dateCreated;
    @UpdateTimestamp
    @Column(name="last_update")
    private Date lastUpdate;
}
