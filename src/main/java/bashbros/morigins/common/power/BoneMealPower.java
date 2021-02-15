package bashbros.morigins.common.power;

import io.github.apace100.origins.power.Active;
import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import bashbros.morigins.common.network.packet.BoneMealPacket;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;

public class BoneMealPower extends Power implements Active {
	private KeyType keyType;
	
	public BoneMealPower(PowerType<?> type, PlayerEntity player) {
		super(type, player);
	}
	
	@Override
	public void onUse() {
		if (player.world.isClient) {
			MinecraftClient client = MinecraftClient.getInstance();
			if (client.crosshairTarget != null && client.crosshairTarget.getType() == HitResult.Type.BLOCK) {
				BoneMealPacket.send(((BlockHitResult) client.crosshairTarget).getBlockPos());
			}
		}
	}
	
	@Override
	public KeyType getKey() {
		return keyType;
	}
	
	@Override
	public void setKey(KeyType keyType) {
		this.keyType = keyType;
	}
}
