package ru.governix.staffmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class GovernixStaffClient implements ClientModInitializer {
    private static KeyBinding openPanel;

    @Override
    public void onInitializeClient() {
        openPanel = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.governix_staff.open_panel",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_SHIFT,
                KeyBinding.Category.MISC
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openPanel.wasPressed()) {
                if (client.player != null && client.world != null) {
                    client.setScreen(new StaffDashboardScreen());
                }
            }
        });
    }

    static void runServerCommand(String command) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.getNetworkHandler() == null) {
            return;
        }
        String normalized = command.startsWith("/") ? command.substring(1) : command;
        if (!normalized.isBlank() && normalized.length() <= 256) {
            client.getNetworkHandler().sendChatCommand(normalized);
        }
    }
}
