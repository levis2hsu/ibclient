package com.aqitrade.ib.services;

import java.util.Optional;

public abstract class ConfigurationService {

    public abstract Optional<String> getString(String key);

    public String getStringOrFail(String key) {
        return getString(key).orElseThrow(() -> new IllegalStateException("Missing configuration property [" + key + "]."));
    }

    public abstract Optional<Integer> getInt(String key);

    public int getIntOrFail(String key) {
        return getInt(key).orElseThrow(() -> new IllegalStateException("Missing configuration property [" + key + "]."));
    }

}
