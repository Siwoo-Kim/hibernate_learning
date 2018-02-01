package com.hibernate.learning.domain;

import lombok.extern.java.Log;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hibernate.learning.domain.UserTest.strategy_context;

@Log
public class CardTest {

    @Test
    public void persist(){

        strategy_context(entityManager -> {

            Member member = Member.builder()
                    .username("user1")
                    .email("user1@email.com").build();

            entityManager.persist(member);

            Card card = Card.builder()
                    .member(member)
                    .expireDate(LocalDate.now().plusMonths(3))
                    .enabled(true).build();

            entityManager.persist(card);

            entityManager.createQuery(
                    "select c from Card c join fetch c.member",Card.class
            ).getResultList().stream().map(Card::toString).forEach(log::warning);

        });
    }

}
