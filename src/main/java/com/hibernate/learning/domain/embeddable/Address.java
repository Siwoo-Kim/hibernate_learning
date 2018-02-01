package com.hibernate.learning.domain.embeddable;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable @Getter @ToString @EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Address {

    private String zipcode;
    private String city;
    private String street;

}
