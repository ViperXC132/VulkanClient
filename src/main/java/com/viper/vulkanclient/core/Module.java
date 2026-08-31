package com.viper.vulkanclient.core;

public abstract class Module {
    private final String id;
    private final String name;
    private final Category category;
    private boolean enabled;

    protected Module(String id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public final String id() { return id; }
    public final String name() { return name; }
    public final Category category() { return category; }
    public final boolean enabled() { return enabled; }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;
        this.enabled = enabled;
        if (enabled) onEnable(); else onDisable();
    }

    public void onEnable() {}
    public void onDisable() {}
    public void onTick() {}
}
