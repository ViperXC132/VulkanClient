package com.viper.vulkanclient.module.hud;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;

/** Single Fabric HUD entrypoint; all drawing stays on Minecraft's GuiGraphics path for VulkanMod. */
public final class ClientHudRenderer {
    private static final Identifier ID = Identifier.fromNamespaceAndPath("vulkanclient", "client_hud");
    private ClientHudRenderer() {}

    public static void register() {
        HudElementRegistry.addLast(ID, ClientHudRenderer::render);
    }

    private static void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        HudRuntime.render(graphics, client);
    }
}
