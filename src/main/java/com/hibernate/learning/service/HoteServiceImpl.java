package com.hibernate.learning.service;
import com.hibernate.learning.domain.Hotel;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;

import java.util.List;

import static com.hibernate.learning.utils.DBUtils.*;

public class HoteServiceImpl implements HotelService {

    EntityManager entityManager;

    public Hotel save(Hotel hotel){

        this.entityManager = entityManager();

        try{
            entityManager.getTransaction().begin();

            Hotel foundHotel = this.entityManager.find(Hotel.class,hotel.getId());

            if(!ObjectUtils.isEmpty(foundHotel)) { throw new IllegalStateException(); }

            this.entityManager.persist(hotel);
            this.entityManager.getTransaction().commit();

            return hotel;

        }catch (Exception e){

            e.printStackTrace();
            this.entityManager.getTransaction().rollback();
            throw e;

        }finally {

            this.entityManager.close();

        }

    }

    @Override
    public List<Hotel> getHotels(){

        entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            List<Hotel> hotels = entityManager.createQuery(
                    "select h from Hotel h order by h.id desc"
            ,Hotel.class).getResultList();

            entityManager.getTransaction().commit();

            return hotels;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }
    }
}










































