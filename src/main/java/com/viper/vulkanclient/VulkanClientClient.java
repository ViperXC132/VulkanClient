package com.viper.vulkanclient;

import com.viper.vulkanclient.core.FeatureRegistry;
import com.viper.vulkanclient.core.Module;
import com.viper.vulkanclient.core.ModuleManager;
import com.viper.vulkanclient.module.hud.ClientHudRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class VulkanClientClient implements ClientModInitializer {
    private static final KeyBinding openMenu = new KeyBinding(
            "key.vulkanclient.open_menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            KeyBinding.Category.MISC
    );
    private static ModuleManager modules;

    @Override
    public void onInitializeClient() {
        modules = FeatureRegistry.create();
        KeyBindingHelper.registerKeyBinding(openMenu);
        ClientHudRenderer.register();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.wasPressed()) {
                if (client.currentScreen == null) client.setScreen(new VulkanClientScreen());
            }
            for (Module module : modules.all()) {
                if (module.enabled()) module.onTick();
            }
        });
    }

    public static MinecraftClient minecraft() {
        return MinecraftClient.getInstance();
    }

    public static ModuleManager modules() {
        if (modules == null) modules = FeatureRegistry.create();
        return modules;
    }
}
