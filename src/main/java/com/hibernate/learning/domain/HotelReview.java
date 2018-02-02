package com.hibernate.learning.domain;


import lombok.*;
import lombok.extern.java.Log;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Log
@Getter @Setter @ToString @Builder
@NoArgsConstructor(access = AccessLevel.PACKAGE) @AllArgsConstructor
@Entity @Table(name = "HOTEL_REVIEW")
public class HotelReview {

    @Id @Column(name="HOTEL_REVIEW_ID")
    @SequenceGenerator(
            name="HOTEL_REVIEW_SEQ_GENERATOR",
            sequenceName = "HOTEL_REVIEW_SEQ",
            allocationSize = 1,initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "HOTEL_REVIEW_SEQ_GENERATOR")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOTEL_ID")
    private Hotel hotel;

    private int rate;

    //COMMENT IS RESERVED WORD. SHOULD CHANGE COLUMN NAME
    @Column(name="REVIEW_CMT")
    private String comment;

    @Builder.Default
    private LocalDateTime ratingDate = LocalDateTime.now();


}
