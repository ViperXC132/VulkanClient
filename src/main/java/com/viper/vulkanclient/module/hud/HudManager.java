package com.viper.vulkanclient.module.hud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HudManager {
    private final List<HudElement> elements = new ArrayList<>();

    public void register(HudElement element) {
        if (elements.stream().anyMatch(existing -> existing.id().equals(element.id()))) {
            throw new IllegalArgumentException("Duplicate HUD element id: " + element.id());
        }
        elements.add(element);
    }

    public List<HudElement> elements() {
        return Collections.unmodifiableList(elements);
    }
}
