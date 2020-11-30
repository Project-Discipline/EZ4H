package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.mcjava.cache.EntityInfo;
import org.meditation.ez4h.translators.BedrockTranslator;

public class MoveEntityAbsolutePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MoveEntityAbsolutePacket packet=(MoveEntityAbsolutePacket)inPacket;
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        EntityInfo entityInfo=client.clientStat.entityInfoMap.get((int)packet.getRuntimeEntityId());
        if(entityInfo!=null) {
            double moveX = position.getX() - entityInfo.x, moveY = (position.getY() - 1.62) - entityInfo.y, moveZ = position.getZ() - entityInfo.z;
            if (BedrockUtils.calcDistance(moveX, moveY, moveZ) < 8) {
                client.javaSession.send(new ServerEntityPositionRotationPacket((int) packet.getRuntimeEntityId(), moveX, moveY, moveZ, rotation.getY(), rotation.getX(), packet.isOnGround()));
            } else {
                if(entityInfo.type.equals("item_entity")){
                    client.javaSession.send(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(), position.getY(), position.getZ(), rotation.getY(), rotation.getX(), packet.isOnGround()));
                }else {
                    client.javaSession.send(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(), position.getY(), position.getZ(), rotation.getY(), rotation.getX(), packet.isOnGround()));
                }
            }
            entityInfo.x = position.getX();
            entityInfo.y = (float) (position.getY() - 1.62);
            entityInfo.z = position.getZ();
            client.javaSession.send(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
    }
}
