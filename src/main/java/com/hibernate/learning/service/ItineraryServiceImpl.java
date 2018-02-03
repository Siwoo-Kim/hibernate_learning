package com.hibernate.learning.service;

import com.hibernate.learning.domain.Itinerary;
import com.hibernate.learning.utils.DBUtils;

import javax.persistence.EntityManager;
import java.util.List;

public class ItineraryServiceImpl implements ItineraryService {



    @Override
    public Itinerary save(Itinerary itinerary) {

        EntityManager entityManager = DBUtils.entityManager();

        try{

            entityManager.getTransaction().begin();

            if(itinerary.getId() == null) {

                entityManager.persist(itinerary);

            }else{

                entityManager.merge(itinerary);

            }

            entityManager.getTransaction().commit();

            return itinerary;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }
    }

    @Override
    public List<Itinerary> findAll() {

        EntityManager entityManager = DBUtils.entityManager();

        try {

            return entityManager.createQuery(
                    "select distinct it from Itinerary it join fetch it.sites", Itinerary.class
            ).getResultList();

        }finally {

            entityManager.close();

        }
    }

    @Override
    public Itinerary findItinerary(String name) {

        EntityManager entityManager = DBUtils.entityManager();

        try {

            return entityManager.createQuery(
                    "select distinct it from Itinerary it join fetch it.sites " +
                            "where it.name = :name", Itinerary.class
            ).setParameter("name", name).getSingleResult();

        }finally {
            entityManager.close();
        }
    }
}
