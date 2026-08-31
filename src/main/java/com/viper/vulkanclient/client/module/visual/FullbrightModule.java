package com.viper.vulkanclient.client.module.visual;

import com.viper.vulkanclient.core.Category;
import com.viper.vulkanclient.core.Module;
import net.minecraft.client.MinecraftClient;

public final class FullbrightModule extends Module {
    private double previousGamma = -1.0;

    public FullbrightModule() { super("fullbright", "Fullbright", Category.VISUAL); }

    @Override public void onEnable() {
        MinecraftClient client = MinecraftClient.getInstance();
        previousGamma = client.options.getGamma().getValue();
        client.options.getGamma().setValue(16.0);
    }

    @Override public void onDisable() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (previousGamma >= 0.0) client.options.getGamma().setValue(previousGamma);
        previousGamma = -1.0;
    }
}
