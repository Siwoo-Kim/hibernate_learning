package com.hibernate.learning.domain;

import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.function.Consumer;

@Log
public class UserTest {


    public static void strategy_context(Consumer<EntityManager> logic){

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{

            entityTransaction.begin();

            logic.accept(entityManager);

            entityTransaction.commit();

        }catch (Exception e){
            e.printStackTrace();
            entityTransaction.rollback();
        }finally {
            entityManager.close();
        }
        entityManagerFactory.close();
    }

    @Test
    public void persist(){

        strategy_context(entityManager -> {
            User user = User.builder()
                    .name("user1").email("user1@email.com")
                    .joinDate(LocalDateTime.now()).build();

            entityManager.persist(user);

            log.warning(user::toString);
        });

    }
}
