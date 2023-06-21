package com.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

public class Packet20010NotifyDebugUser implements Packet {
    public static final PacketConverter CODEC = new PacketConverter() {
        @Override
        public Packet decode(byte[] bytes) {
            return new Packet20010NotifyDebugUser();
        }

        @Override
        public byte[] encode(Packet packet) {
            return new byte[0];
        }
    };
}
