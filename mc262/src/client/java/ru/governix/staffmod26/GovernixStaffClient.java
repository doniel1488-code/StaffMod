package ru.governix.staffmod26;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

public final class GovernixStaffClient implements ClientModInitializer {
    private static final KeyMapping OPEN_PANEL = KeyMappingHelper.registerKeyMapping(
            new KeyMapping(
                    "key.governix_staff.open_panel",
                    InputConstants.Type.KEYBOARD,
                    InputConstants.KEY_RSHIFT,
                    KeyMapping.Category.MISC
            )
    );

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN_PANEL.consumeClick()) {
                if (client.player != null && client.level != null) {
                    client.gui.setScreen(new StaffDashboardScreen());
                }
            }
        });
    }
}
