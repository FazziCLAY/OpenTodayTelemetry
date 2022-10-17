package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Packet20004Login implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            return new Packet20004Login(UUID.fromString(new String(data, StandardCharsets.UTF_8)));
        }

        @Override
        public byte[] encode(Packet packet) {
            Packet20004Login p = (Packet20004Login) packet;
            return p.instanceId.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    private final UUID instanceId;

    public Packet20004Login(UUID instanceId) {
        this.instanceId = instanceId;
    }

    public UUID getInstanceId() {
        return instanceId;
    }

    @Override
    public String toString() {
        return "LoginPacket{" +
                "instanceId=" + instanceId +
                "} " + super.toString();
    }
}
