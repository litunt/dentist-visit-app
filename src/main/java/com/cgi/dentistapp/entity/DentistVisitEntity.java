package com.cgi.dentistapp.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "dentist_visit")
public class DentistVisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "visit_time")
    private LocalDateTime visitTime;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "dentist_id")
    private DentistEntity dentist;

    public DentistVisitEntity() {
    }
}
