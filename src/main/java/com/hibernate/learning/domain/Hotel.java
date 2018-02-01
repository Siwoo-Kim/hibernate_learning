package com.hibernate.learning.domain;

import com.hibernate.learning.domain.embeddable.Address;
import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;

@Log
@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor
@Entity @Table(name = "HI_HOTEL")
public class Hotel {

    @Id @Column(name="HOTEL_ID")
    @SequenceGenerator(
            name="HOTEL_SEQ_GENERATOR",
            sequenceName = "HOTEL_SEQ",
            allocationSize = 1, initialValue = 1)
    @GeneratedValue(generator = "HOTEL_SEQ_GENERATOR")
    private Long id;

    private String name;

    @Access(AccessType.PROPERTY)
    private String description;

    //@Enumerated Annoation for ENUM TYPE
    @Enumerated(EnumType.STRING)
    private Grade grade;

    @Embedded
    private Address address;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="city",column = @Column(name="opt_city")),
            @AttributeOverride(name="street",column = @Column(name="opt_street")),
            @AttributeOverride(name="zipcode",column = @Column(name="opt_zipcode"))})
    private Address optionalAddress;


    public enum Grade{
        STAR1, STAR2, STAR3, STAR4, STAR5
    }

    public String getDescription(){

        return this.description;

    }


}
