package com.viper.vulkanclient.client.profile;

import com.viper.vulkanclient.core.Module;
import com.viper.vulkanclient.core.ModuleManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/** Lightweight, dependency-free module profile storage. */
public final class ProfileManager {
    private final ModuleManager modules;
    private final Path directory;

    public ProfileManager(ModuleManager modules, Path directory) {
        this.modules = modules;
        this.directory = directory;
    }

    public void save(String name) throws IOException {
        Files.createDirectories(directory);
        StringBuilder out = new StringBuilder();
        for (Module module : modules.all()) {
            out.append(module.id()).append('=').append(module.enabled()).append('\n');
        }
        Files.writeString(file(name), out.toString());
    }

    public boolean load(String name) throws IOException {
        Path file = file(name);
        if (!Files.exists(file)) return false;
        for (String line : Files.readAllLines(file)) {
            int split = line.indexOf('=');
            if (split <= 0) continue;
            Module module = modules.find(line.substring(0, split));
            if (module != null) module.setEnabled(Boolean.parseBoolean(line.substring(split + 1)));
        }
        return true;
    }

    public Map<String, Boolean> snapshot() {
        Map<String, Boolean> result = new LinkedHashMap<>();
        for (Module module : modules.all()) result.put(module.id(), module.enabled());
        return result;
    }

    private Path file(String name) {
        String safe = name.replaceAll("[^a-zA-Z0-9._-]", "_");
        return directory.resolve(safe + ".profile");
    }
}
