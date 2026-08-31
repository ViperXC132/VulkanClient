package com.viper.vulkanclient.module.hud;

import com.viper.vulkanclient.VulkanClientClient;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

/**
 * First real HUD renderer. Armor and held-item elements use Minecraft's own ItemStack renderer,
 * so the actual texture, enchantment glint, stack count and durability decoration are preserved.
 */
public final class ClientHudRenderer {
    private static final Identifier ID = Identifier.fromNamespaceAndPath("vulkanclient", "client_hud");

    private ClientHudRenderer() {}

    public static void register() {
        HudElementRegistry.addLast(ID, ClientHudRenderer::render);
    }

    private static void render(GuiGraphics graphics, net.minecraft.client.DeltaTracker tickCounter) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.options.hideGui) return;

        if (enabled("hud_armor_hud")) renderArmor(graphics, client);
        if (enabled("hud_held_item")) renderHeldItem(graphics, client);
        if (enabled("hud_fps")) graphics.drawString(client.font, "FPS: " + client.getFps(), 8, 8, 0xFFFFFFFF, true);
    }

    private static void renderArmor(GuiGraphics graphics, Minecraft client) {
        int x = 8;
        int y = 26;
        for (int slot = 3; slot >= 0; slot--) {
            ItemStack stack = client.player.getInventory().getArmor(slot);
            if (stack.isEmpty()) continue;
            graphics.renderItem(stack, x, y);
            graphics.renderItemDecorations(client.font, stack, x, y);
            y += 20;
        }
    }

    private static void renderHeldItem(GuiGraphics graphics, Minecraft client) {
        ItemStack stack = client.player.getInventory().getSelected();
        if (stack.isEmpty()) return;
        int x = 8;
        int y = 112;
        graphics.renderItem(stack, x, y);
        graphics.renderItemDecorations(client.font, stack, x, y);
    }

    private static boolean enabled(String id) {
        var module = VulkanClientClient.modules().find(id);
        return module != null && module.enabled();
    }
}
