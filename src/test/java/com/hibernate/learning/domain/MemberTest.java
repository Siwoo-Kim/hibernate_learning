package com.hibernate.learning.domain;

import lombok.extern.java.Log;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@Log
public class MemberTest {

    public static void strategy_context(Consumer<EntityManager> consumer){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try{
            entityTransaction.begin();
            assertNotNull(entityManager);

            consumer.accept(entityManager);

            entityTransaction.commit();

        }catch(Exception e){
            entityTransaction.rollback();
        }finally{
            entityManager.close();
        }

        entityManagerFactory.close();
    }

    @Test
    public void logic1(){

        strategy_context((entityManager)->{

            Member member = Member.builder()
                    .username("Siwoo Kim")
                    .age(28)
                    .build();

            entityManager.persist(member);

            member.setAge(25);

            Member foundMember = entityManager.find(Member.class,member.getId());

            assertThat(foundMember,is(member));
            assertThat(foundMember.getAge(),is(25));

            log.warning(foundMember.toString());

            List<Member> memberList = entityManager.createQuery("select m from Member m").getResultList();

            assertThat(memberList.size(),is(1));

            log.warning("Member Size "+memberList.size());

            entityManager.remove(foundMember);

            memberList = entityManager.createQuery("select m from Member m").getResultList();

            assertThat(memberList.size(),is(0));

            log.warning("Member Size "+memberList.size());


        });
    }

}
