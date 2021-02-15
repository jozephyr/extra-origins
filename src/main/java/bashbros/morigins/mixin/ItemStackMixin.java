package bashbros.morigins.mixin;

import bashbros.morigins.common.registry.MOPowers;
import bashbros.morigins.common.registry.MOTags;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
	@Shadow
	public abstract Item getItem();
	
	@Inject(method = "damage(ILnet/minecraft/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
	private <T extends LivingEntity> void damage(int amount, T entity, Consumer<T> breakCallback, CallbackInfo callbackInfo) {
		if (MOPowers.ALL_THAT_GLITTERS.isActive(entity)) {
			Item item = getItem();
			if (item instanceof ToolItem && ((ToolItem) item).getMaterial() == ToolMaterials.GOLD && entity.world.random.nextFloat() < 15 / 16f) {
				callbackInfo.cancel();
			}
			if (item instanceof ArmorItem && ((ArmorItem) item).getMaterial() == ArmorMaterials.GOLD && entity.world.random.nextFloat() < 3 / 4f) {
				callbackInfo.cancel();
			}
		}
	}
	
	@Inject(method = "getTooltip", at = @At("RETURN"))
	private void getTooltip(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> callbackInfo) {
		if (MOPowers.ALL_THAT_GLITTERS.isActive(player)) {
			if (getItem() instanceof ToolItem && ((ToolItem) getItem()).getMaterial() == ToolMaterials.GOLD) {
				callbackInfo.getReturnValue().add(new TranslatableText("tooltip.morigins.damage_bonus", 2).formatted(Formatting.GOLD));
			}
			if (MOTags.GOLDEN_ARMOR.contains(getItem())) {
				callbackInfo.getReturnValue().add(new TranslatableText("tooltip.morigins.damage_reduction", 8).formatted(Formatting.GOLD));
			}
		}
	}
}
