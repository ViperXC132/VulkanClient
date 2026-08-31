package com.viper.vulkanclient.module.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

/** Single Fabric HUD entrypoint using Minecraft's normal GUI rendering path. */
public final class ClientHudRenderer {
    private static final Identifier ID = Identifier.of("vulkanclient", "client_hud");
    private ClientHudRenderer() {}

    public static void register() {
        HudElementRegistry.addLast(ID, ClientHudRenderer::render);
    }

    private static void render(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        HudRuntime.render(context, MinecraftClient.getInstance());
    }
}
