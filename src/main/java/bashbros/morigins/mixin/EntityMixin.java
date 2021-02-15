package bashbros.morigins.mixin;

import bashbros.morigins.common.registry.MOPowers;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public World world;
	
	@Inject(method = "getAir", at = @At("HEAD"), cancellable = true)
	private void getAir(CallbackInfoReturnable<Integer> callbackInfo) {
		if (MOPowers.INORGANIC.isActive(((Entity) (Object) this))) {
			callbackInfo.setReturnValue(0);
		}
	}
}
