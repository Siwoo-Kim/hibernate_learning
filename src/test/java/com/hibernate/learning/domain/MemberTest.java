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
import static org.junit.Assert.*;

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
            e.printStackTrace();
            fail();
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

    @Test
    public void logic2(){

        strategy_context((entityManager) -> {

            //4 STATE - TRANSIENT / MANAGED / DETACHED / REMOVED

            //Transient
            Member member = Member.builder().age(30).username("Kim").build();

            //Managed
            entityManager.persist(member);


            Member foundMember = entityManager.createQuery("select m from Member m where m.id = :id",Member.class)
                    .setParameter("id",member.getId()).getSingleResult();


            assertThat(member,is(foundMember));

            //Detached
            entityManager.detach(member);


        });
    }

    @Test
    public void detect_dirty(){

        strategy_context((entityManager -> {

            Member member = Member.builder()
                    .username("member1").age(30).build();

            entityManager.persist(member);

            String newName = "New Name1";
            int newAge = 99;

            member.setAge(newAge);
            member.setUsername(newName);

            //There is no such update() method. It will automatically update the entity
            //when entitymanager flush

            entityManager.flush();

            Member foundMember = entityManager.find(Member.class,member.getId());

            assertThat(foundMember.getAge(),is(newAge));
            assertThat(foundMember.getUsername(),is(newName));

            log.warning(foundMember::toString);
        }));
    }

}
