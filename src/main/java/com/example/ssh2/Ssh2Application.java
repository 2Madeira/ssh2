package com.example.ssh2;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Ssh2Application {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(Ssh2Application.class, args);
    }

    @Bean
    public InitializingBean printPropertiesBean() {
        return () -> System.out.println(env.getProperty("server.port"));
    }

    @Bean
    public InitializingBean printPropertiesFromValue() {
        return () -> System.out.println("from Value annotation" + serverPort);
    }

}
