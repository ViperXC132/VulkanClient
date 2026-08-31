package com.viper.vulkanclient.client.profile;

import java.util.LinkedHashMap;
import java.util.Map;

public record Profile(String name, Map<String, Boolean> modules) {
    public Profile {
        modules = new LinkedHashMap<>(modules);
    }
}
