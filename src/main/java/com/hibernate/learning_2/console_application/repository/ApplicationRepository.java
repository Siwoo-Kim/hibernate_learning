package com.hibernate.learning_2.console_application.repository;

import java.io.Serializable;

public interface ApplicationRepository <T extends Object>{

    T persist(T t);
    T delete(T t);
    Long count();
    T findOne(Serializable id);
    T findAll();


}
