package com.viper.vulkanclient.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

/** Small, dependency-free configuration store. JSON/config UI can be layered on later without changing modules. */
public final class ClientConfig {
    private final Properties values = new Properties();

    public void set(String key, String value) {
        values.setProperty(key, value);
    }

    public String get(String key, String fallback) {
        return values.getProperty(key, fallback);
    }

    public boolean getBoolean(String key, boolean fallback) {
        return Boolean.parseBoolean(values.getProperty(key, Boolean.toString(fallback)));
    }

    public void load(Path file) {
        if (!Files.isRegularFile(file)) return;
        try (var reader = Files.newBufferedReader(file)) {
            values.load(reader);
        } catch (IOException ignored) {
            // A broken optional config must never prevent Minecraft from starting.
        }
    }

    public void save(Path file) {
        try {
            Path parent = file.getParent();
            if (parent != null) Files.createDirectories(parent);
            try (var writer = Files.newBufferedWriter(file)) {
                values.store(writer, "VulkanClient configuration");
            }
        } catch (IOException ignored) {
            // Configuration persistence is best-effort and must not crash the client.
        }
    }
}
