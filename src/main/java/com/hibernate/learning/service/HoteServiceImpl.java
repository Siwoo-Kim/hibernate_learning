package com.hibernate.learning.service;
import com.hibernate.learning.domain.Hotel;
import com.hibernate.learning.domain.HotelReview;
import com.hibernate.learning.domain.embeddable.Address;
import com.hibernate.learning.utils.DBUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import java.util.List;

import static com.hibernate.learning.utils.DBUtils.*;

public class HoteServiceImpl implements HotelService {

    EntityManager entityManager;

    public Hotel save(Hotel hotel){

        this.entityManager = entityManager();

        try{
            entityManager.getTransaction().begin();

            Hotel foundHotel =null;

            try {

                foundHotel = this.entityManager
                        .createQuery("select h from Hotel h where h.name = :name", Hotel.class)
                        .setParameter("name", hotel.getName()).getSingleResult();

            }catch (NoResultException e){

            }

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
    public Hotel getHotelProxy(Long id){

        entityManager = entityManager();

        try{
            entityManager.getTransaction().begin();

            Hotel hotel = entityManager.getReference(Hotel.class,id);

            entityManager.getTransaction().commit();

            return  hotel;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }

    }

    @Override
    public Hotel getHotelByName(String name) {

        entityManager = entityManager();

        try{
            entityManager.getTransaction().begin();

            Hotel hotel = entityManager.createQuery(
                    "select h from Hotel h where h.name = :name",Hotel.class
            ).setParameter("name",name).getSingleResult();

            entityManager.getTransaction().commit();

            return  hotel;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

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

    @Override
    public Hotel addAddress(String name,Address address) {

        entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            Hotel hotel = entityManager.createQuery(
                    "select h from Hotel h where h.name = :name",Hotel.class
            ).setParameter("name",name).getSingleResult();

            hotel.setAddress(address);

            entityManager.getTransaction().commit();

            return hotel;


        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }

    }


    @Override
    public Hotel addOptionalAddress(String name, Address address) {

        entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            Hotel hotel = entityManager.createQuery(
                    "select h from Hotel h where h.name = :name",Hotel.class
            ).setParameter("name",name).getSingleResult();

            if(ObjectUtils.isEmpty(hotel.getAddress())){
                throw new IllegalStateException();
            }

            hotel.setOptionalAddress(address);

            entityManager.getTransaction().commit();

            return hotel;


        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }

    }

    @Override
    public HotelReview addReview(HotelReview hotelReview) {

        entityManager = entityManager();

        try{

            entityManager.getTransaction().begin();

            if(ObjectUtils.isEmpty(hotelReview.getHotel())){
                throw new IllegalStateException("HotelReview must contain hotel entity");
            }

            entityManager.persist(hotelReview);

            entityManager.getTransaction().commit();

            return hotelReview;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }

    }

    @Override
    public List<HotelReview> getReviews(String hotelName){

        entityManager = currentEntityManager();

        try{

            entityManager.getTransaction().begin();

            List<HotelReview> hotelReviewList = entityManager.createQuery(
                    "select hr from HotelReview hr join hr.hotel h " +
                            "where h.name = :hotelName",HotelReview.class
            ).setParameter("hotelName",hotelName).getResultList();


            entityManager.getTransaction().commit();

            return  hotelReviewList;

        }catch (Exception e){

            e.printStackTrace();
            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            closeCurrentEntityMananger();

        }
    }

}










































