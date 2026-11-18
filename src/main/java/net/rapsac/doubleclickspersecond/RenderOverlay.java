package net.rapsac.doubleclickspersecond;

import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;

public class RenderOverlay {
	public static int xOffset = 0;
	public static int yOffset = 15;
	public static int cps = 0;

	// Matches the HudElement functional interface: (GuiGraphics, DeltaTracker)
	public static void onHudRender(GuiGraphics graphics, DeltaTracker deltaTracker) {
		cps = InputHandler.Cps();
		if (DoubleClicksPerSecondClient.ShouldRenderOverlay()) {
			render(graphics);
		}
	}

	public static void render(GuiGraphics graphics) {
		Minecraft client = Minecraft.getInstance();
		if (client == null) return;
		Font font = client.font;
		String text = Integer.toString(cps);
		graphics.drawString(font, text, XValue(client, font, text), YValue(client), color(), false);
	}

	public static int XValue(Minecraft client, Font font, String text) {
		return (int) (client.getWindow().getGuiScaledWidth() / 2f + xOffset - font.width(text) / 2f);
	}

	public static int YValue(Minecraft client) {
		return (int) (client.getWindow().getGuiScaledHeight() / 2f + yOffset);
	}

	// ARGB. Since 1.21.6 an alpha of 0 is no longer treated as opaque,
	// so black must be written as 0xFF000000 (it was plain 0 in 1.19.4).
	public static int color() {
		return InputHandler.WasLastDouble() ? 0xFF00FF00 : 0xFF000000;
	}
}
