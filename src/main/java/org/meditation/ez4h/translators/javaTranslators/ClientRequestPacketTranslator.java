package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientRequestPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientRequestPacket packet=(ClientRequestPacket)inPacket;
        switch (packet.getRequest()){
            case RESPAWN:{
                RespawnPacket respawnPacket=new RespawnPacket();
                respawnPacket.setPosition(Vector3f.from(0,0,0));
                respawnPacket.setRuntimeEntityId(client.clientStat.entityId);
                respawnPacket.setState(RespawnPacket.State.CLIENT_READY);
                client.bedrockSession.sendPacket(respawnPacket);
                break;
            }
        }
    }
}
