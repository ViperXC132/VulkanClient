package com.viper.vulkanclient;

public enum ModuleCategory {
    GENERAL("General"),
    VISUAL("Visual"),
    HUD("HUD"),
    PVP("PvP"),
    HYPIXEL("Hypixel"),
    QOL("QoL"),
    SETTINGS("Settings");

    private final String title;

    ModuleCategory(String title) {
        this.title = title;
    }

    public String title() {
        return title;
    }
}
