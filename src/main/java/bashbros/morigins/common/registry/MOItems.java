package bashbros.morigins.common.registry;

import bashbros.morigins.common.Morigins;
import bashbros.morigins.common.item.LiquidSunlightItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class MOItems {
	private static final Map<Item, Identifier> ITEMS = new LinkedHashMap<>();
	
	public static final Item LIQUID_SUNLIGHT = create("liquid_sunlight", new LiquidSunlightItem(new Item.Settings().group(ItemGroup.FOOD).maxCount(16)));
	
	private static <T extends Item> T create(String name, T item) {
		ITEMS.put(item, new Identifier(Morigins.MODID, name));
		return item;
	}
	
	public static void init() {
		ITEMS.keySet().forEach(item -> Registry.register(Registry.ITEM, ITEMS.get(item), item));
	}
}
