package me.miquiis.skinchangerapi.client;

import me.miquiis.skinchangerapi.server.network.messages.LoadSkinPacket;

public class PacketHandler {

    public static void handleLoadSkinPacket(LoadSkinPacket msg) {
       SkinChangerAPIClient.loadSkin(msg.getSkinLocation());
    }
}
