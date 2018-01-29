package com.hibernate.learning.service;

import com.hibernate.learning.domain.User;
import com.hibernate.learning.utils.DBUtils;
import lombok.Getter;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    @Getter
    public static class DuplicateEmailException extends RuntimeException{
        public String code;

        public DuplicateEmailException(String message,String code) {
            super(message); this.code = code;
        }

    }

    @Getter
    public static class UserNotFoundException extends RuntimeException{
        public String code;

        public UserNotFoundException(String message,String code){
            super(message); this.code = code;
        }
    }

    @Override
    public void join(User user) {
        EntityManager entityManager = DBUtils.entityManager();
        entityManager.getTransaction().begin();

        try{

            User foundUser = entityManager.find(User.class,user.getEmail());

            if(!ObjectUtils.isEmpty(foundUser)){
                throw new DuplicateEmailException("Email is duplicated","duplicate.user.email");
            }

            entityManager.persist(user);
            entityManager.getTransaction().commit();

        }catch(Exception e){

            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;

        }finally {

            entityManager.close();

        }
    }

    @Override
    public Optional<User> user(String email) {

        EntityManager entityManager = DBUtils.entityManager();

        try{

            User user = entityManager.find(User.class,email);
            return Optional.ofNullable(user);

        }finally {

            entityManager.close();

        }

    }

    @Override
    public void changeName(String email, String newName) {

        EntityManager entityManager = DBUtils.entityManager();

        try{

            entityManager.getTransaction().begin();

            User foundUser = entityManager.find(User.class,email);
            if(ObjectUtils.isEmpty(foundUser)) {  throw new UserNotFoundException("User does not exist","notExist.user.email"); }

            foundUser.setName(newName);

            entityManager.getTransaction().commit();

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;

        }finally {

            entityManager.close();

        }
    }

    @Override
    public List<User> getUsers(){

        EntityManager entityManager = DBUtils.entityManager();

        try{

            entityManager.getTransaction().begin();

            TypedQuery<User> query = entityManager.createQuery("select u from User u order by u.name",User.class);
            List<User> users = query.getResultList();

            entityManager.getTransaction().commit();
            return users;

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            throw e;

        }finally {

            entityManager.close();

        }
    }

    @Override
    public void remove(String email) {

        EntityManager entityManager = DBUtils.entityManager();
        entityManager.getTransaction().begin();

        try{
            User foundUser = entityManager.find(User.class,email);


            if(ObjectUtils.isEmpty(foundUser)) {  throw new UserNotFoundException("User does not exist","notExist.user.email"); }

            entityManager.remove(foundUser);
            entityManager.getTransaction().commit();

        }catch (Exception e){

            entityManager.getTransaction().rollback();
            e.printStackTrace();
            throw e;

        }finally {

            entityManager.close();

        }

    }

}































