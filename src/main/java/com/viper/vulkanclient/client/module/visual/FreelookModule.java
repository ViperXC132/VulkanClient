package com.viper.vulkanclient.client.module.visual;

import com.viper.vulkanclient.client.module.Module;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;

/** Camera-only freelook: decouples view rotation from player rotation while held. */
public final class FreelookModule extends Module {
    private float cameraYaw;
    private float cameraPitch;
    private boolean active;

    public FreelookModule() { super("freelook", "Freelook", Category.VISUAL); }

    public void begin() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        active = true;
        cameraYaw = client.player.getYaw();
        cameraPitch = client.player.getPitch();
    }

    public void update(float deltaYaw, float deltaPitch) {
        if (!active) return;
        cameraYaw += deltaYaw;
        cameraPitch = Math.max(-90.0f, Math.min(90.0f, cameraPitch + deltaPitch));
    }

    public void end() { active = false; }
    public boolean isActive() { return active; }
    public float getCameraYaw() { return cameraYaw; }
    public float getCameraPitch() { return cameraPitch; }
    public Perspective getPerspective() { return MinecraftClient.getInstance().options.getPerspective(); }
}
