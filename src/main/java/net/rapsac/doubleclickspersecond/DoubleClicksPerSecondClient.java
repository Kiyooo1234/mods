package net.rapsac.doubleclickspersecond;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoubleClicksPerSecondClient implements ClientModInitializer {
	public static final String MOD_ID = "doubleclickspersecond";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static boolean shouldRenderOverlayBool = false;

	// Since 1.21.9 key mappings need a registered Category instead of a plain string
	private static final KeyMapping.Category CATEGORY =
			KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "main"));

	@Override
	public void onInitializeClient() {
		KeyMapping toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
				"key." + MOD_ID + ".toggle_cps",
				InputConstants.Type.KEYSYM,
				-1, // unbound by default, same as before
				CATEGORY
		));

		// HudRenderCallback is deprecated; HudElementRegistry replaces it
		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				Identifier.fromNamespaceAndPath(MOD_ID, "cps_overlay"),
				RenderOverlay::onHudRender
		);

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			// consumeClick() was wasPressed() in Yarn: true once per key press
			while (toggleKey.consumeClick()) {
				shouldRenderOverlayBool = !shouldRenderOverlayBool;
			}
		});
	}

	public static boolean ShouldRenderOverlay() {
		return shouldRenderOverlayBool;
	}
}
