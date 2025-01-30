package com.creativemd.creativecore.common.packet;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;

import com.creativemd.littletiles.LittleTiles;

public class PacketHandler {

    public static void sendPacketToAllPlayers(CreativeCorePacket packet) {
        LittleTiles.network.sendToAll(new CreativeMessageHandler(packet));
    }

    public static void sendPacketToServer(CreativeCorePacket packet) {
        LittleTiles.network.sendToServer(new CreativeMessageHandler(packet));
    }

    public static void sendPacketsToAllPlayers(ArrayList<CreativeCorePacket> packets) {
        for (int i = 0; i < packets.size(); i++) {
            sendPacketToAllPlayers(packets.get(i));
        }
    }

    public static void sendPacketToPlayer(CreativeCorePacket packet, EntityPlayerMP player) {
        LittleTiles.network.sendTo(new CreativeMessageHandler(packet), player);
    }

}
