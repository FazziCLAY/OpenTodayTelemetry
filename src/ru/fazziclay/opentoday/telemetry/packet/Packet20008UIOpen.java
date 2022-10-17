package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

public class Packet20008UIOpen implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            return new Packet20008UIOpen();
        }

        @Override
        public byte[] encode(Packet packet) {
            return new byte[0];
        }
    }

    public Packet20008UIOpen() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
