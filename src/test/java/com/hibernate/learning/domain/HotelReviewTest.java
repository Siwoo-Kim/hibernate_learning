package com.hibernate.learning.domain;

import lombok.extern.java.Log;
import org.junit.Test;

import static com.hibernate.learning.domain.UserTest.strategy_context;

@Log
public class HotelReviewTest {

    @Test
    public void persist(){

        strategy_context(entityManager -> {

            Hotel hotel = Hotel.builder().build();

            entityManager.persist(hotel);

            log.warning(hotel::toString);

            HotelReview hotelReview = HotelReview.builder()
                    .hotel(hotel)
                    .build();

            entityManager.persist(hotelReview);

            log.warning(hotelReview::toString);
        });
    }
}
