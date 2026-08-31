package com.viper.vulkanclient.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public void register(Module module) {
        if (modules.stream().anyMatch(existing -> existing.id().equals(module.id()))) {
            throw new IllegalArgumentException("Duplicate module id: " + module.id());
        }
        modules.add(module);
    }

    public List<Module> all() {
        return Collections.unmodifiableList(modules);
    }

    public List<Module> byCategory(Category category) {
        return modules.stream().filter(module -> module.category() == category).toList();
    }

    public Module find(String id) {
        return modules.stream().filter(module -> module.id().equals(id)).findFirst().orElse(null);
    }
}
