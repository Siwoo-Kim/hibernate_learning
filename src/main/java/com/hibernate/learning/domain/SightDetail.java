package com.hibernate.learning.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter @AllArgsConstructor @ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class SightDetail {


    @Max(24) @Min(0)
    @Column(table = "SIGHT_DETAIL")
    private Integer hoursOfOperation;

    @Column(table = "SIGHT_DETAIL")
    private String holiday;

    @Column(table = "SIGHT_DETAIL")
    private String facilities;

}
