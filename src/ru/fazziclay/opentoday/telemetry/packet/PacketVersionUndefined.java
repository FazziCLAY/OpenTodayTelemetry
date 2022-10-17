package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

import java.nio.charset.StandardCharsets;

public class PacketVersionUndefined implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            return new PacketVersionUndefined();
        }

        @Override
        public byte[] encode(Packet packet) {
            return "Please send the first PacketSetVersion!".getBytes(StandardCharsets.UTF_8);
        }
    }
}
