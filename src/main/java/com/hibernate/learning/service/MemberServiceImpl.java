package com.hibernate.learning.service;

import com.hibernate.learning.domain.Member;
import com.hibernate.learning.domain.MemberDetail;

import javax.persistence.EntityManager;
import java.util.List;

import static com.hibernate.learning.utils.DBUtils.entityManager;

public class MemberServiceImpl implements MemberService {


    @Override
    public Member save(Member member) {

        EntityManager entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            try {

                entityManager.createQuery(
                        "select m from Member m where m.email = :email", Member.class
                ).setParameter("email", member.getEmail())
                .getSingleResult();

                throw new IllegalStateException("Member Email is duplicated");

            }catch(Exception e){
                /* Ignore */
            }

            entityManager.persist(member);

            entityManager.getTransaction().commit();

            return member;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }

    }

    @Override
    public Member find(String email) {

        EntityManager entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();


            Member foundMember = entityManager.createQuery(
                        "select m from Member m where m.email = :email", Member.class
                ).setParameter("email", email)
                        .getSingleResult();




            entityManager.getTransaction().commit();

            return foundMember;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }

    }

    @Override
    public List<Member> findAll() {

        EntityManager entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();


            List<Member> foundMembers = entityManager.createQuery(
                    "select m from Member m",Member.class
            ).getResultList();

            entityManager.getTransaction().commit();

            return foundMembers;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }
    }

    @Override
    public MemberDetail addDetail(MemberDetail memberDetail) {

        EntityManager entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();


            entityManager.persist(memberDetail);

            entityManager.getTransaction().commit();

            return memberDetail;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }
    }
}
