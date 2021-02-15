package bashbros.morigins.client;

import bashbros.morigins.common.registry.MOItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;

@Environment(EnvType.CLIENT)
public class MoriginsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ColorProviderRegistryImpl.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? 0xffff00 : 0xffffff, MOItems.LIQUID_SUNLIGHT);
	}
}
