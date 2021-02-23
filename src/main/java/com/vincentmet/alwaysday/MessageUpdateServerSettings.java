package com.vincentmet.alwaysday;

import java.util.function.Supplier;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageUpdateServerSettings{
	public boolean always_night;
	
	public MessageUpdateServerSettings(boolean always_night){
		this.always_night = always_night;
	}
	
	public static void encode(MessageUpdateServerSettings packet, PacketBuffer buffer){
		buffer.writeBoolean(packet.always_night);
	}
	
	public static MessageUpdateServerSettings decode(PacketBuffer buffer) {
		return new MessageUpdateServerSettings(buffer.readBoolean());
	}
	
	public static void handle(final MessageUpdateServerSettings message, Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Config.ServerToClientSyncedConfig.ALWAYS_NIGHT = message.always_night;
		});
		ctx.get().setPacketHandled(true);
	}
}
