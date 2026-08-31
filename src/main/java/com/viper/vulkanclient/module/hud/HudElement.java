package com.viper.vulkanclient.module.hud;

import net.minecraft.client.gui.DrawContext;

public interface HudElement {
    String id();
    String name();
    void render(DrawContext context, float tickDelta);
}
