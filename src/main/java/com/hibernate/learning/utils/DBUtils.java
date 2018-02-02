package com.hibernate.learning.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtils {
    private static EntityManagerFactory entityManagerFactory;
    private static ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    public static void init()
    {
        DBUtils.entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    public static EntityManager entityManager(){

        return DBUtils.entityManagerFactory.createEntityManager();
    }

    public static EntityManager currentEntityManager(){
        EntityManager entityManager = entityManagerThreadLocal.get();
        if(entityManager == null){
            entityManager = entityManagerFactory.createEntityManager();
            entityManagerThreadLocal.set(entityManager);
        }
        return entityManager;
    }

    public static void closeCurrentEntityMananger(){
        EntityManager entityManager = entityManagerThreadLocal.get();
        if(entityManager != null){
            entityManagerThreadLocal.remove();
            entityManager.close();
        }
    }

    public static void close(){
        DBUtils.entityManagerFactory.close();
    }

}
