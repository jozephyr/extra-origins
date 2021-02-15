package bashbros.morigins.common;

import bashbros.morigins.common.network.packet.BoneMealPacket;
import bashbros.morigins.common.registry.MOConditions;
import bashbros.morigins.common.registry.MOItems;
import bashbros.morigins.common.registry.MOPowers;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class morigins implements ModInitializer {
	public static final String MODID = "morigins";
	
	@Override
	public void onInitialize() {
		ServerPlayNetworking.registerGlobalReceiver(BoneMealPacket.ID, BoneMealPacket::handle);
		MOItems.init();
		MOPowers.init();
		MOConditions.init();
	}
}
