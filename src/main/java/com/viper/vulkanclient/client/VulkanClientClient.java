package com.viper.vulkanclient.client;

import com.viper.vulkanclient.client.hud.HudManager;
import com.viper.vulkanclient.client.module.visual.FreelookModule;
import com.viper.vulkanclient.client.module.visual.FullbrightModule;
import com.viper.vulkanclient.client.module.visual.LowFireModule;
import com.viper.vulkanclient.client.module.visual.LowShieldModule;
import com.viper.vulkanclient.client.module.visual.ZoomModule;
import com.viper.vulkanclient.core.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

/** Secondary client bootstrap kept for compatibility with older source layout. */
public final class VulkanClientClient implements ClientModInitializer {
    public static final ModuleManager MODULES = new ModuleManager();
    public static final HudManager HUD = new HudManager();
    public static ProfileManager PROFILES;

    @Override
    public void onInitializeClient() {
        if (MODULES.all().isEmpty()) {
            MODULES.register(new FreelookModule());
            MODULES.register(new FullbrightModule());
            MODULES.register(new ZoomModule());
            MODULES.register(new LowFireModule());
            MODULES.register(new LowShieldModule());
        }
        HUD.registerDefaults();
        PROFILES = new ProfileManager(MODULES, FabricLoader.getInstance().getConfigDir().resolve("vulkanclient/profiles"));
    }
}
