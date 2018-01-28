package com.hibernate.learning.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtils {
    private static EntityManagerFactory entityManagerFactory;

    public static void init(){
        DBUtils.entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
    }

    public static EntityManager entityManager(){
        return DBUtils.entityManagerFactory.createEntityManager();
    }

    public static void close(){
        DBUtils.entityManagerFactory.close();
    }
}
