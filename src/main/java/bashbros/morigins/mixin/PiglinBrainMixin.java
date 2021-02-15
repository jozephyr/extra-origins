package bashbros.morigins.mixin;

import bashbros.morigins.common.registry.MOPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinBrain.class)
public abstract class PiglinBrainMixin {
	@Inject(method = "wearsGoldArmor", at = @At("HEAD"), cancellable = true)
	private static void wearsGoldArmor(LivingEntity target, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (MOPowers.PIGLIN_NEUTRALITY.isActive(target)) {
			callbackInfo.setReturnValue(true);
		}
	}
	
	@Inject(method = "onGuardedBlockInteracted", at = @At("HEAD"), cancellable = true)
	private static void onGuardedBlockInteracted(PlayerEntity player, boolean blockOpen, CallbackInfo callbackInfo) {
		if (MOPowers.PIGLIN_NEUTRALITY.isActive(player)) {
			callbackInfo.cancel();
		}
	}
}
