package com.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.Util;
import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;

import java.nio.charset.StandardCharsets;

public class Packet20007DataFixerLogs implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {

            int ver = Util.bytesToInt(new byte[]{data[0], data[1], data[2], data[3]});
            byte[] logsB = new byte[data.length - 4];


            System.arraycopy(data, 4, logsB, 0, data.length-4);

            String logs = new String(logsB, StandardCharsets.UTF_8);


            return new Packet20007DataFixerLogs(ver, logs);
        }

        @Override
        public byte[] encode(Packet packet) {
            Packet20007DataFixerLogs p = (Packet20007DataFixerLogs) packet;

            byte[] ver = Util.intToBytes(p.dataVersion);
            byte[] logs = ((Packet20007DataFixerLogs) packet).logs.getBytes(StandardCharsets.UTF_8);
            byte[] result = new byte[ver.length + logs.length];

            System.arraycopy(ver, 0, result, 0, ver.length);
            System.arraycopy(logs, 0, result, 4, logs.length);

            return result;
        }
    }

    private final int dataVersion;
    private final String logs;

    public Packet20007DataFixerLogs(int dataVersion, String logs) {
        this.dataVersion = dataVersion;
        this.logs = logs;
    }

    public int getDataVersion() {
        return dataVersion;
    }

    public String getLogs() {
        return logs;
    }

    @Override
    public String toString() {
        return "DataFixerLogsPacket{" +
                "dataVersion=" + dataVersion +
                ", logs='" + logs + '\'' +
                "} " + super.toString();
    }
}
