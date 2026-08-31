package com.viper.vulkanclient.client.module.visual;

import com.viper.vulkanclient.client.module.Module;

/** Zoom state is consumed by the client camera mixin/render hook. */
public final class ZoomModule extends Module {
    public static final float DEFAULT_ZOOM = 4.0f;
    private float zoom = DEFAULT_ZOOM;

    public ZoomModule() { super("zoom", "Zoom", Category.VISUAL); }
    public float getZoom() { return zoom; }
    public void setZoom(float zoom) { this.zoom = Math.max(1.0f, Math.min(20.0f, zoom)); }
}
