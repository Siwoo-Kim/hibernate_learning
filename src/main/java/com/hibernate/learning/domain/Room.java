package com.hibernate.learning.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @Table(name="HI_ROOM")
public class Room {

    @Id
    @Column(name="ROOM_ID")
    private Long id;

    private String name;

    private String description;

}
