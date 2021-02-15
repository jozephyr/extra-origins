package bashbros.morigins.mixin;

import bashbros.morigins.common.interfaces.BabyAccessor;
import bashbros.morigins.common.registry.MOPowers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements BabyAccessor {
	private static final TrackedData<Boolean> BABY = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	
	@Shadow
	public abstract HungerManager getHungerManager();
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}
	
	@Override
	public boolean getBaby() {
		return dataTracker.get(BABY);
	}
	
	@Override
	public void setBaby(boolean baby) {
		dataTracker.set(BABY, baby);
	}
	
	@Inject(method = "tick", at = @At("TAIL"))
	private void tick(CallbackInfo callbackInfo) {
		if (MOPowers.ABSORBING.isActive(this) && random.nextFloat() < 1 / 20f) {
			heal(1);
		}
		if (MOPowers.INORGANIC.isActive(this)) {
			getHungerManager().setFoodLevel(0);
			if (world.isClient) {
				getHungerManager().setSaturationLevelClient(20);
			}
			if (age % 100 == 0) {
				heal(1);
			}
		}
	}
	
	@Inject(method = "getHurtSound", at = @At("HEAD"), cancellable = true)
	private void getHurtSound(DamageSource source, CallbackInfoReturnable<SoundEvent> callbackInfo) {
		if (MOPowers.INORGANIC.isActive(this)) {
			callbackInfo.setReturnValue(SoundEvents.BLOCK_GLASS_HIT);
		}
	}
	
	@Inject(method = "getDeathSound", at = @At("HEAD"), cancellable = true)
	private void getDeathSound(CallbackInfoReturnable<SoundEvent> callbackInfo) {
		if (MOPowers.INORGANIC.isActive(this)) {
			callbackInfo.setReturnValue(SoundEvents.BLOCK_GLASS_BREAK);
		}
	}
	
	@Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
	private void isInvulnerableTo(DamageSource damageSource, CallbackInfoReturnable<Boolean> callbackInfo) {
		if (damageSource.isFire() && MOPowers.HOMESICK.isActive(this) && !world.getDimension().isPiglinSafe()) {
			callbackInfo.setReturnValue(true);
		}
	}
	
	@Inject(method = "readCustomDataFromTag", at = @At("TAIL"))
	private void readCustomDataFromTag(CompoundTag tag, CallbackInfo callbackInfo) {
		setBaby(tag.getBoolean("Baby"));
	}
	
	@Inject(method = "writeCustomDataToTag", at = @At("TAIL"))
	private void writeCustomDataToTag(CompoundTag tag, CallbackInfo callbackInfo) {
		tag.putBoolean("Baby", getBaby());
	}
	
	@Inject(method = "initDataTracker", at = @At("TAIL"))
	private void initDataTracker(CallbackInfo callbackInfo) {
		dataTracker.startTracking(BABY, false);
	}
}
