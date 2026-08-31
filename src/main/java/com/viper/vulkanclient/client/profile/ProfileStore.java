package com.viper.vulkanclient.client.profile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ProfileStore {
    private final Path directory;

    public ProfileStore(Path directory) {
        this.directory = directory;
        try {
            Files.createDirectories(directory);
        } catch (IOException ignored) {
        }
    }

    public void save(String name, String data) throws IOException {
        Files.writeString(directory.resolve(name + ".profile"), data);
    }

    public String load(String name) throws IOException {
        return Files.readString(directory.resolve(name + ".profile"));
    }

    public boolean exists(String name) {
        return Files.isRegularFile(directory.resolve(name + ".profile"));
    }
}
