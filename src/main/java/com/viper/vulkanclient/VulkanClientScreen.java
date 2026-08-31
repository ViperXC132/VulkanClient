package com.viper.vulkanclient;

import com.viper.vulkanclient.core.Category;
import com.viper.vulkanclient.core.Module;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.List;

/** Transparent, connected sidebar GUI using Yarn mappings and Minecraft's DrawContext API. */
public final class VulkanClientScreen extends Screen {
    private static final int MAIN_WIDTH = 220;
    private static final int MODULE_WIDTH = 300;
    private static final int SETTINGS_WIDTH = 320;
    private static final int GAP = 6;
    private static final int TOP = 10;
    private static final int BOTTOM = 10;

    private Category selectedCategory = Category.GENERAL;
    private Module selectedModule;

    public VulkanClientScreen() {
        super(Text.literal("VulkanClient"));
    }

    @Override
    protected void init() {
        selectedCategory = Category.GENERAL;
        selectedModule = null;
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        int mainX = 8;
        int mainY = TOP;
        int panelHeight = Math.max(120, this.height - TOP - BOTTOM);

        drawPanel(graphics, mainX, mainY, MAIN_WIDTH, panelHeight, 0xD910141B);
        drawText(graphics, "VULKANCLIENT", mainX + 16, mainY + 16, 0xFFFFFFFF);
        drawText(graphics, "1.21.11  •  Vulkan ready", mainX + 16, mainY + 34, 0xFF8F9CAF);

        int y = mainY + 62;
        for (Category category : Category.values()) {
            boolean active = category == selectedCategory;
            drawRow(graphics, mainX + 8, y, MAIN_WIDTH - 16, 34, pretty(category), active, mouseX, mouseY);
            y += 38;
        }

        int moduleX = mainX + MAIN_WIDTH + GAP;
        drawPanel(graphics, moduleX, mainY, MODULE_WIDTH, panelHeight, 0xD90F141B);
        drawText(graphics, pretty(selectedCategory), moduleX + 16, mainY + 16, 0xFFFFFFFF);
        drawText(graphics, "Modules", moduleX + 16, mainY + 34, 0xFF8F9CAF);

        List<Module> modules = VulkanClientClient.modules().byCategory(selectedCategory);
        y = mainY + 62;
        for (Module module : modules) {
            boolean active = module == selectedModule;
            drawRow(graphics, moduleX + 8, y, MODULE_WIDTH - 16, 34, module.name(), active, mouseX, mouseY);
            if (module.enabled()) {
                graphics.fill(moduleX + MODULE_WIDTH - 28, y + 10, moduleX + MODULE_WIDTH - 16, y + 22, 0xFF79D6A1);
            }
            y += 38;
            if (y > mainY + panelHeight - 34) break;
        }

        if (selectedModule != null) {
            int settingsX = moduleX + MODULE_WIDTH + GAP;
            drawPanel(graphics, settingsX, mainY, SETTINGS_WIDTH, panelHeight, 0xD90C1118);
            drawText(graphics, selectedModule.name(), settingsX + 16, mainY + 16, 0xFFFFFFFF);
            drawText(graphics, pretty(selectedModule.category()), settingsX + 16, mainY + 34, 0xFF8F9CAF);

            int toggleY = mainY + 68;
            drawText(graphics, "Enabled", settingsX + 16, toggleY + 9, 0xFFDDE5F0);
            drawToggle(graphics, settingsX + SETTINGS_WIDTH - 66, toggleY, selectedModule.enabled(), mouseX, mouseY);

            drawText(graphics, "Settings", settingsX + 16, toggleY + 58, 0xFFFFFFFF);
            drawText(graphics, "Feature settings are registered here.", settingsX + 16, toggleY + 80, 0xFF8F9CAF);
            drawText(graphics, "Renderer: Minecraft DrawContext", settingsX + 16, toggleY + 101, 0xFF8F9CAF);
            drawText(graphics, "Vulkan-safe path: enabled", settingsX + 16, toggleY + 122, 0xFF8F9CAF);
        }

        super.render(graphics, mouseX, mouseY, delta);
    }

    private void drawPanel(DrawContext graphics, int x, int y, int width, int height, int color) {
        graphics.fill(x, y, x + width, y + height, color);
        graphics.fill(x, y, x + width, y + 1, 0x334FFFFF);
        graphics.fill(x, y + height - 1, x + width, y + height, 0x223FFFFF);
    }

    private void drawRow(DrawContext graphics, int x, int y, int width, int height, String label,
                         boolean selected, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        int fill = selected ? 0xB82D394A : hovered ? 0x55313B4D : 0x0018202C;
        graphics.fill(x, y, x + width, y + height, fill);
        if (selected) graphics.fill(x, y, x + 3, y + height, 0xFF8AB4FF);
        drawText(graphics, label, x + 12, y + 11, selected ? 0xFFFFFFFF : 0xFFD2D9E4);
    }

    private void drawToggle(DrawContext graphics, int x, int y, boolean enabled, int mouseX, int mouseY) {
        boolean hovered = mouseX >= x && mouseX <= x + 50 && mouseY >= y && mouseY <= y + 22;
        graphics.fill(x, y, x + 50, y + 22, enabled ? 0xFF4C8A6C : 0xFF343B46);
        if (hovered) graphics.fill(x, y, x + 50, y + 1, 0xFF8AB4FF);
        int knobX = enabled ? x + 30 : x + 4;
        graphics.fill(knobX, y + 4, knobX + 16, y + 18, 0xFFF2F5F9);
    }

    private void drawText(DrawContext graphics, String text, int x, int y, int color) {
        graphics.drawTextWithShadow(this.textRenderer, text, x, y, color);
    }

    private static String pretty(Category category) {
        String value = category.name().toLowerCase(java.util.Locale.ROOT);
        return Character.toUpperCase(value.charAt(0)) + value.substring(1);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        double mouseX = click.x();
        double mouseY = click.y();
        if (click.button() != GLFW.GLFW_MOUSE_BUTTON_LEFT) return super.mouseClicked(click, doubled);

        int mainX = 8;
        int mainY = TOP;
        int y = mainY + 62;
        for (Category category : Category.values()) {
            if (inside(mouseX, mouseY, mainX + 8, y, MAIN_WIDTH - 16, 34)) {
                selectedCategory = category;
                selectedModule = null;
                return true;
            }
            y += 38;
        }

        int moduleX = mainX + MAIN_WIDTH + GAP;
        y = mainY + 62;
        List<Module> modules = VulkanClientClient.modules().byCategory(selectedCategory);
        for (Module module : modules) {
            if (inside(mouseX, mouseY, moduleX + 8, y, MODULE_WIDTH - 16, 34)) {
                selectedModule = module;
                return true;
            }
            y += 38;
            if (y > mainY + this.height - TOP - BOTTOM - 34) break;
        }

        if (selectedModule != null) {
            int settingsX = moduleX + MODULE_WIDTH + GAP;
            if (inside(mouseX, mouseY, settingsX + SETTINGS_WIDTH - 66, mainY + 68, 50, 22)) {
                selectedModule.setEnabled(!selectedModule.enabled());
                return true;
            }
        }

        return super.mouseClicked(click, doubled);
    }

    private static boolean inside(double mouseX, double mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (input.key() == GLFW.GLFW_KEY_ESCAPE) {
            close();
            return true;
        }
        return super.keyPressed(input);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
