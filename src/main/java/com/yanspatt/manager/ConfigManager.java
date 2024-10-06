package com.yanspatt.manager;

import com.yanspatt.loader.ConfigLoader;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConfigManager {

    private static final Map<String, Object> configCache = new ConcurrentHashMap<>();


    public <T> T getConfig(String configName, ConfigLoader<T> loader, String filePath) throws IOException {
        if (!configCache.containsKey(configName)) {
            T config = loader.loadConfig(filePath);
            configCache.put(configName, config);
        }
        return (T) configCache.get(configName);
    }
}
