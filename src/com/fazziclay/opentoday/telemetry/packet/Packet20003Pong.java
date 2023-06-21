package com.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

import java.nio.charset.StandardCharsets;

public class Packet20003Pong implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {

        @Override
        public Packet decode(byte[] data) {
            return new Packet20003Pong(new String(data, StandardCharsets.UTF_8));
        }
        @Override
        public byte[] encode(Packet packet) {
            Packet20003Pong p = (Packet20003Pong) packet;
            return p.message.getBytes(StandardCharsets.UTF_8);
        }

    }
    private final String message;

    public Packet20003Pong(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "PacketPong{" +
                "message='" + message + '\'' +
                "} " + super.toString();
    }
}
