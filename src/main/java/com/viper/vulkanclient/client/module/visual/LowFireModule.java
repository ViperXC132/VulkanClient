package com.viper.vulkanclient.client.module.visual;

import com.viper.vulkanclient.client.module.Module;

/** Marker module for the low-fire renderer hook. Rendering is handled by the client mixin layer. */
public final class LowFireModule extends Module {
    public LowFireModule() { super("low_fire", "Low Fire", Category.VISUAL); }
}
