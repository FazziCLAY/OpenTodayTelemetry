package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.Util;
import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

public class Packet20005Handshake implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            return new Packet20005Handshake(Util.bytesToInt(data));
        }

        @Override
        public byte[] encode(Packet packet) {
            Packet20005Handshake p = (Packet20005Handshake) packet;
            return Util.intToBytes(p.appVersion);
        }
    }

    private final int appVersion;

    public Packet20005Handshake(int appVersion) {
        this.appVersion = appVersion;
    }

    public int getAppVersion() {
        return appVersion;
    }
}
