package com.viper.vulkanclient;

import com.mojang.blaze3d.platform.InputConstants;
import com.viper.vulkanclient.core.FeatureRegistry;
import com.viper.vulkanclient.core.Module;
import com.viper.vulkanclient.core.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class VulkanClientClient implements ClientModInitializer {
    private static final KeyMapping.Category KEY_CATEGORY = KeyMapping.Category.create("key.category.vulkanclient.client");
    private static KeyMapping openMenu;
    private static ModuleManager modules;

    @Override
    public void onInitializeClient() {
        modules = FeatureRegistry.create();
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.vulkanclient.open_menu",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KEY_CATEGORY
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new VulkanClientScreen());
                }
            }
            for (Module module : modules.all()) {
                if (module.enabled()) module.onTick();
            }
        });
    }

    public static Minecraft minecraft() {
        return Minecraft.getInstance();
    }

    public static ModuleManager modules() {
        if (modules == null) modules = FeatureRegistry.create();
        return modules;
    }
}
