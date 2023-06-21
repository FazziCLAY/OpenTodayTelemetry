package com.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

public class Packet20009UIClosed implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            return new Packet20009UIClosed();
        }

        @Override
        public byte[] encode(Packet packet) {
            return new byte[0];
        }
    }

    public Packet20009UIClosed() {
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
