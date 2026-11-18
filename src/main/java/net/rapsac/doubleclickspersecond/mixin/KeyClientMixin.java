package net.rapsac.doubleclickspersecond.mixin;

import net.minecraft.client.Minecraft;
import net.rapsac.doubleclickspersecond.InputHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class KeyClientMixin {

	// Was MinecraftClient.doAttack() in Yarn
	@Inject(method = "startAttack", at = @At("HEAD"))
	private void onAttack(CallbackInfoReturnable<Boolean> cir) {
		InputHandler.Clicking();
	}

	// Was MinecraftClient.doItemUse() in Yarn
	@Inject(method = "startUseItem", at = @At("HEAD"))
	private void onItemUse(CallbackInfo ci) {
		InputHandler.Clicking();
	}
}
