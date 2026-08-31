package com.viper.vulkanclient.module.hud;

import com.viper.vulkanclient.VulkanClientClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/** Shared HUD runtime using Minecraft's normal Yarn DrawContext APIs. */
public final class HudRuntime {
    private HudRuntime() {}

    public static void render(DrawContext g, MinecraftClient mc) {
        if (mc.player == null || mc.options.hudHidden) return;
        int x = 8, y = 8;
        y = text(g, mc, "hud_fps", "FPS: " + mc.getCurrentFps(), x, y);
        y = text(g, mc, "hud_server_ip", server(mc), x, y);
        y = text(g, mc, "hud_coordinates", "XYZ: " + fmt(mc.player.getX()) + " " + fmt(mc.player.getY()) + " " + fmt(mc.player.getZ()), x, y);
        y = text(g, mc, "hud_speed_bps", "BPS: " + fmt(speed(mc.player)), x, y);
        y = text(g, mc, "hud_yaw_pitch", "Yaw/Pitch: " + fmt(mc.player.getYaw()) + " / " + fmt(mc.player.getPitch()), x, y);
        y = text(g, mc, "hud_facing_direction", "Facing: " + mc.player.getHorizontalFacing().asString(), x, y);
        y = text(g, mc, "hud_health", "Health: " + fmt(mc.player.getHealth()), x, y);
        y = text(g, mc, "hud_hunger", "Food: " + mc.player.getHungerManager().getFoodLevel(), x, y);
        y = text(g, mc, "hud_xp_level", "Level: " + mc.player.experienceLevel, x, y);
        y = text(g, mc, "hud_dimension", "Dimension: " + mc.player.getEntityWorld().getRegistryKey().getValue(), x, y);
        y = text(g, mc, "hud_real_time", java.time.LocalTime.now().withNano(0).toString(), x, y);
        y = text(g, mc, "hud_keystrokes", keys(mc), x, y);
        y = text(g, mc, "hud_cps", "CPS: " + ClickCounter.cps(), x, y);
        if (enabled("hud_armor_hud")) renderArmor(g, mc, 8, 180);
        if (enabled("hud_held_item")) renderHeld(g, mc, 8, 270);
    }

    private static int text(DrawContext g, MinecraftClient mc, String id, String value, int x, int y) {
        if (!enabled(id)) return y;
        g.drawTextWithShadow(mc.textRenderer, value, x, y, 0xFFFFFFFF);
        return y + 11;
    }

    private static void renderArmor(DrawContext g, MinecraftClient mc, int x, int y) {
        for (int slot = 3; slot >= 0; slot--) {
            ItemStack stack = mc.player.getInventory().armor.get(slot);
            if (stack.isEmpty()) continue;
            g.drawItem(stack, x, y);
            g.drawItemInSlot(mc.textRenderer, stack, x, y);
            y += 20;
        }
    }

    private static void renderHeld(DrawContext g, MinecraftClient mc, int x, int y) {
        ItemStack stack = mc.player.getMainHandStack();
        if (stack.isEmpty()) return;
        g.drawItem(stack, x, y);
        g.drawItemInSlot(mc.textRenderer, stack, x, y);
        g.drawTextWithShadow(mc.textRenderer, stack.getName(), x + 20, y + 4, 0xFFFFFFFF);
    }

    private static String keys(MinecraftClient mc) {
        return "W" + down(mc.options.forwardKey) + " A" + down(mc.options.leftKey) + " S" + down(mc.options.backKey) + " D" + down(mc.options.rightKey) + " | SPACE" + down(mc.options.jumpKey);
    }

    private static String down(net.minecraft.client.option.KeyBinding key) { return key.isPressed() ? "[X]" : "[ ]"; }
    private static double speed(PlayerEntity p) { double dx = p.getX() - p.lastX, dz = p.getZ() - p.lastZ; return Math.sqrt(dx * dx + dz * dz) * 20.0; }
    private static String server(MinecraftClient mc) { if (mc.isInSingleplayer()) return "Singleplayer"; if (mc.getCurrentServerEntry() == null) return "Server: offline"; return "Server: " + mc.getCurrentServerEntry().address; }
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
