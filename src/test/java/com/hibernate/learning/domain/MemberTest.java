package com.hibernate.learning.domain;

import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import static org.junit.Assert.assertNotNull;

public class MemberTest {

    @Test
    public void persistTest(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{
            entityTransaction.begin();
            assertNotNull(entityManager);

            Member member = Member.builder().age(10).username("Siwoo").build();
            entityManager.persist(member);


            entityTransaction.commit();

        }catch(Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }

        entityManagerFactory.close();
    }
}
