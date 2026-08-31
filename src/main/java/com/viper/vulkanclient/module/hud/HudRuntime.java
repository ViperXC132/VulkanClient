package com.viper.vulkanclient.module.hud;

import com.viper.vulkanclient.VulkanClientClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/** Shared Vulkan-safe HUD runtime using Minecraft's GuiGraphics APIs only. */
public final class HudRuntime {
    private HudRuntime() {}

    public static void render(GuiGraphics g, Minecraft mc) {
        if (mc.player == null || mc.options.hideGui) return;
        int x = 8, y = 8;
        y = text(g, mc, "hud_fps", "FPS: " + mc.getFps(), x, y);
        y = text(g, mc, "hud_server_ip", server(mc), x, y);
        y = text(g, mc, "hud_coordinates", "XYZ: " + fmt(mc.player.getX()) + " " + fmt(mc.player.getY()) + " " + fmt(mc.player.getZ()), x, y);
        y = text(g, mc, "hud_speed_bps", "BPS: " + fmt(speed(mc.player)), x, y);
        y = text(g, mc, "hud_yaw_pitch", "Yaw/Pitch: " + fmt(mc.player.getYRot()) + " / " + fmt(mc.player.getXRot()), x, y);
        y = text(g, mc, "hud_facing_direction", "Facing: " + mc.player.getDirection().getName(), x, y);
        y = text(g, mc, "hud_health", "Health: " + fmt(mc.player.getHealth()), x, y);
        y = text(g, mc, "hud_hunger", "Food: " + mc.player.getFoodData().getFoodLevel(), x, y);
        y = text(g, mc, "hud_xp_level", "Level: " + mc.player.experienceLevel, x, y);
        y = text(g, mc, "hud_dimension", "Dimension: " + mc.player.level().dimension().location(), x, y);
        y = text(g, mc, "hud_real_time", java.time.LocalTime.now().withNano(0).toString(), x, y);
        y = text(g, mc, "hud_keystrokes", keys(mc), x, y);
        y = text(g, mc, "hud_cps", "CPS: " + ClickCounter.cps(), x, y);
        if (enabled("hud_armor_hud")) renderArmor(g, mc, 8, 180);
        if (enabled("hud_held_item")) renderHeld(g, mc, 8, 270);
    }

    private static int text(GuiGraphics g, Minecraft mc, String id, String value, int x, int y) {
        if (!enabled(id)) return y;
        g.drawString(mc.font, value, x, y, 0xFFFFFFFF, true);
        return y + 11;
    }

    private static void renderArmor(GuiGraphics g, Minecraft mc, int x, int y) {
        for (int slot = 3; slot >= 0; slot--) {
            ItemStack stack = mc.player.getInventory().getArmor(slot);
            if (stack.isEmpty()) continue;
            g.renderItem(stack, x, y);
            g.renderItemDecorations(mc.font, stack, x, y);
            y += 20;
        }
    }

    private static void renderHeld(GuiGraphics g, Minecraft mc, int x, int y) {
        ItemStack stack = mc.player.getInventory().getSelected();
        if (stack.isEmpty()) return;
        g.renderItem(stack, x, y);
        g.renderItemDecorations(mc.font, stack, x, y);
        g.drawString(mc.font, stack.getHoverName(), x + 20, y + 4, 0xFFFFFFFF, true);
    }

    private static String keys(Minecraft mc) {
        return "W" + down(mc.options.keyUp) + " A" + down(mc.options.keyLeft) + " S" + down(mc.options.keyDown) + " D" + down(mc.options.keyRight) + " | SPACE" + down(mc.options.keyJump);
    }

    private static String down(net.minecraft.client.KeyMapping key) { return key.isDown() ? "[X]" : "[ ]"; }
    private static double speed(Player p) { double dx = p.getX() - p.xOld, dz = p.getZ() - p.zOld; return Math.sqrt(dx * dx + dz * dz) * 20.0; }
    private static String server(Minecraft mc) { if (mc.hasSingleplayerServer()) return "Singleplayer"; if (mc.getCurrentServer() == null) return "Server: offline"; return "Server: " + mc.getCurrentServer().ip; }
    private static String fmt(double v) { return String.format(java.util.Locale.ROOT, "%.2f", v); }
    private static boolean enabled(String id) { var m = VulkanClientClient.modules().find(id); return m != null && m.enabled(); }

    public static final class ClickCounter {
        private static long windowStart = System.currentTimeMillis();
        private static int clicks;
        private ClickCounter() {}
        public static void record() { clicks++; }
        public static int cps() { long now = System.currentTimeMillis(); if (now - windowStart >= 1000L) { clicks = 0; windowStart = now; } return clicks; }
    }
}
