package bashbros.morigins.common.registry;

import bashbros.morigins.common.morigins;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.item.Item;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class MOTags {
	public static final Tag<Item> GOLDEN_ARMOR = TagRegistry.item(new Identifier(morigins.MODID, "golden_armor"));
}
