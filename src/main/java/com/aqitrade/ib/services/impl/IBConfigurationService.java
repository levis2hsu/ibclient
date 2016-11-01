package com.aqitrade.ib.services.impl;


import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.aqitrade.ib.services.ConfigurationService;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

@Service
public class IBConfigurationService extends ConfigurationService {

    Config conf;

    @PostConstruct
    private void init(){
        conf = ConfigFactory.parseResources(this.getClass().getClassLoader(), "ibclient.conf").resolve();
    }

    @Override
    public Optional<String> getString(String key) {
        if(conf.hasPath(key)) return Optional.of(conf.getString(key));
        else return Optional.empty();
    }

    @Override
    public Optional<Integer> getInt(String key) {
        if(conf.hasPath(key)) return Optional.of(conf.getInt(key));
        else return Optional.empty();
    }

}
