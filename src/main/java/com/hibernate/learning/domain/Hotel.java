package com.hibernate.learning.domain;

import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name = "HI_HOTEL")
public class Hotel {

    @Id
    @Column(name="HOTEL_ID")
    private String id;

    private String name;

    @Access(AccessType.PROPERTY)
    private String description;

    //@Enumerated Annoation for ENUM TYPE
    @Enumerated(EnumType.STRING)
    private Grade grade;


    public enum Grade{
        STAR1, STAR2, STAR3, STAR4, STAR5
    }

    public String getDescription(){

        return this.description;

    }


}
