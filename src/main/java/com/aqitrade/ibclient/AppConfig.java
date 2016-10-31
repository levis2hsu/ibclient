package com.aqitrade.ibclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import akka.actor.ActorSystem;

@Configuration
public class AppConfig {

    @Bean(name = "actorsystem")
    @Scope("singleton")
    public ActorSystem actorSystem() {
        return ActorSystem.create("IBClient");
    }

}
