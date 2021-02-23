package com.vincentmet.alwaysday;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler{
	private static int messageID = 0;
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	
	public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
			.named(new ResourceLocation(BaseClass.MODID, "network"))
			.clientAcceptedVersions(PROTOCOL_VERSION::equals)
			.serverAcceptedVersions(PROTOCOL_VERSION::equals)
			.networkProtocolVersion(() -> PROTOCOL_VERSION)
			.simpleChannel();
	
	private static int nextID() {
		return messageID++;
	}
	
	public static void init() {
		CHANNEL.registerMessage(nextID(), MessageUpdateServerSettings.class, MessageUpdateServerSettings::encode, MessageUpdateServerSettings::decode, MessageUpdateServerSettings::handle);
	}
}
