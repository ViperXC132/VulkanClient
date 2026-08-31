package com.viper.vulkanclient;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/**
 * First-pass navigation shell for the VulkanClient GUI.
 * The UI deliberately uses Minecraft's GuiGraphics pipeline rather than direct OpenGL calls,
 * keeping the foundation friendly to VulkanMod.
 */
public final class VulkanClientScreen extends Screen {
    private static final int MAIN_WIDTH = 250;
    private static final int CHILD_WIDTH = 300;
    private static final int PANEL_GAP = 8;
    private static final int PANEL_TOP = 18;
    private static final int PANEL_BOTTOM = 18;

    private ModuleCategory selectedCategory;
    private String selectedModule;

    public VulkanClientScreen() {
        super(Component.literal("VulkanClient"));
    }

    @Override
    protected void init() {
        selectedCategory = ModuleCategory.GENERAL;
        selectedModule = null;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        // Do not draw a full-screen opaque background: the Minecraft world remains visible.
        int mainX = 12;
        int mainY = PANEL_TOP;
        int height = this.height - PANEL_TOP - PANEL_BOTTOM;

        drawPanel(graphics, mainX, mainY, MAIN_WIDTH, height, 0xD9141820);
        drawText(graphics, "VULKANCLIENT", mainX + 18, mainY + 18, 0xFFFFFFFF);
        drawText(graphics, "1.21.11 • Vulkan-ready", mainX + 18, mainY + 36, 0xFF91A0B5);

        int y = mainY + 68;
        for (ModuleCategory category : ModuleCategory.values()) {
            boolean active = category == selectedCategory;
            drawRow(graphics, mainX + 10, y, MAIN_WIDTH - 20, 34, category.title(), active, mouseX, mouseY);
            y += 39;
        }

        int childX = mainX + MAIN_WIDTH + PANEL_GAP;
        drawPanel(graphics, childX, mainY, CHILD_WIDTH, height, 0xD910151C);
        drawText(graphics, selectedCategory.title(), childX + 18, mainY + 18, 0xFFFFFFFF);
        drawText(graphics, "Modules", childX + 18, mainY + 38, 0xFF91A0B5);

        List<String> modules = modulesFor(selectedCategory);
        y = mainY + 68;
        for (String module : modules) {
            boolean active = module.equals(selectedModule);
            drawRow(graphics, childX + 10, y, CHILD_WIDTH - 20, 34, module, active, mouseX, mouseY);
            y += 39;
        }

        if (selectedModule != null) {
            int settingsX = childX + CHILD_WIDTH + PANEL_GAP;
            drawPanel(graphics, settingsX, mainY, CHILD_WIDTH, height, 0xD90E131A);
            drawText(graphics, selectedModule, settingsX + 18, mainY + 18, 0xFFFFFFFF);
            drawText(graphics, "Module settings", settingsX + 18, mainY + 38, 0xFF91A0B5);
            drawText(graphics, "Settings panel ready", settingsX + 18, mainY + 76, 0xFFD5DCE7);
            drawText(graphics, "Vulkan-safe rendering foundation", settingsX + 18, mainY + 98, 0xFF91A0B5);
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    private void drawPanel(GuiGraphics graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x, y, x + width, y + height, color);
        graphics.fill(x, y, x + width, y + 1, 0x334FFFFFF);
        graphics.fill(x, y + height - 1, x + width, y + height, 0x223FFFFFF);
    }

    private void drawRow(GuiGraphics graphics, int x, int y, int width, int height, String label,
                         boolean selected, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int color = selected ? 0xB82E394A : hovered ? 0x55313B4D : 0x0018202C;
        graphics.fill(x, y, x + width, y + height, color);
        if (selected) {
            graphics.fill(x, y, x + 3, y + height, 0xFF8AB4FF);
        }
        drawText(graphics, label, x + 12, y + 11, selected ? 0xFFFFFFFF : 0xFFD2D9E4);
    }

    private void drawText(GuiGraphics graphics, String text, int x, int y, int color) {
        graphics.drawString(this.font, Component.literal(text), x, y, color, false);
    }

    private List<String> modulesFor(ModuleCategory category) {
        return switch (category) {
            case GENERAL -> List.of("Client Settings", "Profiles", "Keybinds");
            case VISUAL -> List.of("Fullbright", "Freelook", "Zoom", "Motion Blur", "Low Fire", "Low Shield", "Nametags", "Beacon Beams", "Hit Color", "Time Changer");
            case HUD -> List.of("HUD Editor", "FPS", "Ping", "TPS", "Keystrokes", "CPS", "Armor", "Held Item", "Coordinates", "Speed", "Crosshair", "Scoreboard", "Bossbar", "Actionbar", "Hotbar");
            case PVP -> List.of("Attack Indicator", "Reach", "Combo Counter", "Hit Counter", "Target HUD", "Potion Effects", "Item Cooldown");
            case HYPIXEL -> List.of("AutoGG", "AutoGF", "AutoGLHF", "AutoTip", "AutoBoop", "LevelHead");
            case QOL -> List.of("Screenshot Manager", "Screenshot Upload", "Scrollable Tooltips", "Nick Hider", "Chat Timestamps", "Auto Reconnect", "Quick Disconnect");
            case SETTINGS -> List.of("Interface", "HUD Layouts", "Rendering", "Accessibility", "Config Import / Export");
        };
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int mainX = 12;
        int mainY = PANEL_TOP;
        int y = mainY + 68;

        for (ModuleCategory category : ModuleCategory.values()) {
            if (mouseX >= mainX + 10 && mouseX <= mainX + MAIN_WIDTH - 10 && mouseY >= y && mouseY <= y + 34) {
                selectedCategory = category;
                selectedModule = null;
                return true;
            }
            y += 39;
        }

        int childX = mainX + MAIN_WIDTH + PANEL_GAP;
        y = mainY + 68;
        for (String module : modulesFor(selectedCategory)) {
            if (mouseX >= childX + 10 && mouseX <= childX + CHILD_WIDTH - 10 && mouseY >= y && mouseY <= y + 34) {
                selectedModule = module;
                return true;
            }
            y += 39;
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
