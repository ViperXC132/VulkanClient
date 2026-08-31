package com.viper.vulkanclient.core;

/** Central, deterministic feature catalog. */
public final class FeatureRegistry {
    private FeatureRegistry() {}

    public static ModuleManager create() {
        ModuleManager manager = new ModuleManager();
        register(manager, Category.GENERAL, false, "Client Settings", "Profiles", "Keybinds");
        register(manager, Category.VISUAL, true,
                "Fullbright", "Freelook", "Zoom", "Motion Blur", "Low Fire", "Low Shield", "Nametags", "Beacon Beams", "Hit Color", "Time Changer", "Weather Changer", "Block Outline", "Custom Block Outline", "Waypoints", "Tracers", "Hitboxes", "Player ESP", "Mob ESP", "Item ESP", "Entity Outlines", "Health Bars", "Entity Distance", "Armor Display", "No Hurt Camera", "No FOV Effects", "FOV Modifier", "Dynamic FOV", "No Fog", "Custom Fog", "No Vignette", "No Pumpkin Overlay", "No Blindness", "No Nausea", "Low Hurt Effect", "Particle Multiplier", "Damage Tint", "Hit Particles", "Critical Particles", "Damage Numbers", "Item Physics", "Dropped Item Names", "Item Rarity Colors", "Item Glint", "Durability Display", "UI Blur", "World Blur", "Bloom", "Color Correction", "Saturation", "Contrast", "Brightness", "Vignette");
        register(manager, Category.HUD, false,
                "HUD Editor", "FPS", "Ping", "TPS", "Server IP", "Server Address", "Memory / RAM", "Chunk Updates", "Keystrokes", "CPS", "Attack Indicator", "Reach", "Combo Counter", "Hit Counter", "Potion Effects", "Target HUD", "Target Info", "Armor HUD", "Held Item", "Item Durability", "Item Cooldown", "Coordinates", "Speed / BPS", "Velocity", "Yaw / Pitch", "Facing Direction", "Player Name", "Player Head", "Distance Traveled", "Session Time", "World Time", "Real Time", "Weather", "Dimension", "Compass", "Looking At Block", "Looking At Entity", "Custom Crosshair", "Custom Bossbar", "Custom Actionbar", "Custom Scoreboard", "Custom Hotbar", "Custom Health", "Custom Hunger", "Custom XP", "Custom Armor", "Custom Chat", "Custom Tablist", "Inventory Preview", "Item Pickup", "Item Name", "Item Rarity", "Container Preview", "Server Brand", "Client Brand", "World Session Time", "Current Pack", "Pack Display", "Screenshot Info", "Discord Status");
        register(manager, Category.PVP, false,
                "Keystrokes", "CPS", "Attack Indicator", "Reach", "Combo Counter", "Hit Counter", "Potion Effects", "Target HUD", "Target Info", "Crosshair", "Armor HUD", "Held Item", "Item Durability", "Item Cooldown", "Freelook", "Zoom", "Motion Blur", "Low Fire", "Low Shield");
        register(manager, Category.HYPIXEL, false,
                "AutoGG", "AutoGF", "AutoGLHF", "AutoTip", "AutoBoop", "LevelHead", "BedWars Stats", "SkyWars Stats", "Duels Stats", "Game Detection", "Game Mode Display", "Hypixel Level", "Bed Status", "Final Kills", "Session Stats", "W/L", "FKDR");
        register(manager, Category.QOL, false,
                "Screenshot Manager", "Screenshot Upload", "Scrollable Tooltips", "Nick Hider", "Chat Timestamps", "Chat Cleaner", "Auto Reconnect", "FPS Limiter", "Quick Disconnect", "Inventory Preview", "Container Preview", "Searchable Settings", "Config Profiles", "Config Import / Export", "Keybind Manager");
        register(manager, Category.SETTINGS, false, "Interface", "HUD Layouts", "Rendering", "Accessibility", "Config Import / Export");
        return manager;
    }

    private static void register(ModuleManager manager, Category category, boolean renderFeature, String... names) {
        for (String name : names) manager.register(new FeatureModule(id(category, name), name, category, renderFeature));
    }

    private static String id(Category category, String name) {
        return category.name().toLowerCase(java.util.Locale.ROOT) + "_" + name.toLowerCase(java.util.Locale.ROOT).replaceAll("[^a-z0-9]+", "_").replaceAll("^_|_$", "");
    }
}
