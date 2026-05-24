package com.pulsevisuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class PulseVisualsMod implements ClientModInitializer {

    public static boolean menuOpen = false;
    public static boolean hudEnabled = true;
    public static boolean killauraIcon = true;
    public static boolean speedIcon = true;
    public static boolean flyIcon = false;

    private static final Identifier ICONS = Identifier.of("pulsevisuals", "textures/gui/icons.png");

    @Override
    public void onInitializeClient() {
        KeyBinding menuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.pulsevisuals.menu",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_RIGHT_SHIFT,
            "category.pulsevisuals.main"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (menuKey.wasPressed()) {
                menuOpen = !menuOpen;
                if (client.player != null) {
                    client.player.sendMessage(
                        net.minecraft.text.Text.literal(
                            menuOpen ? "§6[§ePulse Visuals§6] §aMenu Open" : "§6[§ePulse Visuals§6] §cMenu Closed"
                        ), 
                        true
                    );
                }
            }
        });

        HudRenderCallback.EVENT.register((DrawContext context, float tickDelta) -> {
            if (!hudEnabled) return;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.currentScreen != null) return;

            int x = 10, y = 10, size = 16, gap = 4;

            if (killauraIcon) {
                context.drawTexture(ICONS, x, y, 0, 0, size, size, 256, 256);
                x += size + gap;
            }
            if (speedIcon) {
                context.drawTexture(ICONS, x, y, 16, 0, size, size, 256, 256);
                x += size + gap;
            }
            if (flyIcon) {
                context.drawTexture(ICONS, x, y, 32, 0, size, size, 256, 256);
            }
        });
    }
}
