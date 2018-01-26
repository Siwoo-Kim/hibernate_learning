package com.hibernate.learning;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@ComponentScan("com.hibernate.learning")
public class WebConfig  extends WebMvcConfigurationSupport{

}
