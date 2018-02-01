package com.hibernate.learning.service;

import com.hibernate.learning.domain.Sight;
import com.hibernate.learning.domain.SightDetail;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import static com.hibernate.learning.utils.DBUtils.entityManager;

public class SightServiceImpl implements SightService {


    @Override
    public Sight save(Sight sight) {

        EntityManager entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            try {

                Sight foundSight = entityManager.createQuery(
                        "select s from Sight s where s.name = :name", Sight.class
                ).setParameter("name", sight.getName()).getSingleResult();

                //AFTER THIS LINE IN THE BLOCK SHOULD NOT EXECUTE, BECAUSE OF NAME SHOULD NOT BE DUPLICATED
                throw new IllegalStateException();

            }catch (NoResultException e){

            }

            entityManager.persist(sight);

            entityManager.getTransaction().commit();
            return  sight;

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;

        }

    }

    @Override
    public Sight addSightDetail(Long id, SightDetail sightDetail) {
        return null;
    }
}
