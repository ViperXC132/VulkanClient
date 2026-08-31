package com.viper.vulkanclient.core;

/** A named client feature. Rendering-heavy features are intentionally implemented behind this boundary. */
public final class FeatureModule extends Module {
    private final boolean renderFeature;

    public FeatureModule(String id, String name, Category category) {
        this(id, name, category, false);
    }

    public FeatureModule(String id, String name, Category category, boolean renderFeature) {
        super(id, name, category);
        this.renderFeature = renderFeature;
    }

    public boolean renderFeature() {
        return renderFeature;
    }
}
