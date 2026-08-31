package com.viper.vulkanclient.module.hud;

import net.minecraft.client.gui.GuiGraphics;

/** Base contract for every draggable HUD element. */
public interface HudElement {
    String id();
    String name();
    void render(GuiGraphics graphics, float tickDelta);
}
