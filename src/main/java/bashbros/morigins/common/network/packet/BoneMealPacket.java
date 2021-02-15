package bashbros.morigins.common.network.packet;

import io.netty.buffer.Unpooled;
import bashbros.morigins.common.Morigins;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class BoneMealPacket {
	public static final Identifier ID = new Identifier(Morigins.MODID, "bone_meal");
	
	public static void send(BlockPos pos) {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeLong(pos.asLong());
		ClientPlayNetworking.send(ID, buf);
	}
	
	public static void handle(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler network, PacketByteBuf buf, PacketSender sender) {
		BlockPos pos = BlockPos.fromLong(buf.readLong());
		server.execute(() -> {
			if (player.isCreative() || player.getHungerManager().getFoodLevel() > 0) {
				BlockState state = player.world.getBlockState(pos);
				if (state.getBlock() instanceof Fertilizable) {
					Fertilizable fertilizable = ((Fertilizable) state.getBlock());
					if (fertilizable.canGrow(player.world, player.getRandom(), pos, state)) {
						fertilizable.grow(player.getServerWorld(), player.getRandom(), pos, state);
						player.world.syncWorldEvent(2005, pos, 0);
						player.swingHand(Hand.MAIN_HAND, true);
						player.addExhaustion(3);
					}
				}
			}
		});
	}
}
