package com.viper.vulkanclient;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class VulkanClient implements ModInitializer {
    public static final String MOD_ID = "vulkanclient";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("VulkanClient core initialized");
    }
}
