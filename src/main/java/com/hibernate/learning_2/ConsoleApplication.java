package com.hibernate.learning_2;

import com.hibernate.learning_2.console_application.RootConfig;
import com.hibernate.learning_2.console_application.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
public class ConsoleApplication {



    public static void main(String[] args){

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
        MemberRepository memberRepository = applicationContext.getBean(MemberRepository.class);

        System.out.println(memberRepository.toString());


    }
}
