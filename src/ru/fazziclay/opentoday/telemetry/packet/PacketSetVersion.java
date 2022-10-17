package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.Util;
import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

public class PacketSetVersion implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {

        @Override
        public Packet decode(byte[] data) {
            return new PacketSetVersion(Util.bytesToInt(data));
        }
        @Override
        public byte[] encode(Packet packet) {
            PacketSetVersion p = (PacketSetVersion) packet;
            return Util.intToBytes(p.version);
        }

    }
    private final int version;

    public int getVersion() {
        return version;
    }

    public PacketSetVersion(int version) {
        this.version = version;
    }
}
