package com.hibernate.learning_2;

import com.hibernate.learning_2.console_application.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= RootConfig.class)
public class ContextTest {

   @PersistenceUnit
   EntityManagerFactory entityManagerFactory;

   @Test
   public void test(){

       EntityManager entityManager = entityManagerFactory.createEntityManager();


   }

}
