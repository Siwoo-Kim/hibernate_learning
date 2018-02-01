package com.hibernate.learning.service;

import com.hibernate.learning.domain.Card;
import com.hibernate.learning.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;

import static com.hibernate.learning.utils.DBUtils.entityManager;

public class CardServiceImpl implements CardService {

    MemberService memberService = new MemberServiceImpl();

    @Override
    public void save(Card card) {

        EntityManager entityManager = entityManager();

        try {

            entityManager.getTransaction().begin();

            entityManager.persist(card);

            entityManager.getTransaction().commit();

        }catch(Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }
    }

    @Override
    public void assign(Member member, Card card) {

        EntityManager entityManager = entityManager();

        try {

            entityManager.getTransaction().begin();

            card.assignTo(member);

            entityManager.getTransaction().commit();

        }catch(Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }

    }

    @Override
    public Card find(String cardNumber) {
        EntityManager entityManager = entityManager();

        try {

            entityManager.getTransaction().begin();

            Card foundCard = entityManager
                    .createQuery(
                            "select c from Card c where c.cardNumber = :cardNumber",Card.class
                    ).setParameter("cardNumber",cardNumber).getSingleResult();


            entityManager.getTransaction().commit();

            return foundCard;

        }catch(Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }
    }

}
