package com.viper.vulkanclient.client;

import com.viper.vulkanclient.client.module.ModuleManager;
import com.viper.vulkanclient.client.module.visual.FreelookModule;
import com.viper.vulkanclient.client.module.visual.FullbrightModule;
import com.viper.vulkanclient.client.module.visual.ZoomModule;
import com.viper.vulkanclient.client.module.visual.LowFireModule;
import com.viper.vulkanclient.client.module.visual.LowShieldModule;
import com.viper.vulkanclient.client.hud.HudManager;
import net.fabricmc.api.ClientModInitializer;

public final class VulkanClientClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    public static final HudManager HUD = new HudManager();

    @Override
    public void onInitializeClient() {
        MODULES.register(new FreelookModule());
        MODULES.register(new FullbrightModule());
        MODULES.register(new ZoomModule());
        MODULES.register(new LowFireModule());
        MODULES.register(new LowShieldModule());
        HUD.registerDefaults();
    }
}
