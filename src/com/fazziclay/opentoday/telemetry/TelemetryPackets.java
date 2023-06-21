package com.fazziclay.opentoday.telemetry;

import com.fazziclay.neosocket.packet.PacketsRegistry;
import com.fazziclay.opentoday.telemetry.packet.*;

public class TelemetryPackets extends PacketsRegistry {
    public TelemetryPackets() {
        super(new Info[]{
                new Info(0, PacketSetVersion.class, PacketSetVersion.CONVERTER),
                new Info(1, PacketVersionUndefined.class, PacketVersionUndefined.CONVERTER),
                new Info(20002, Packet20002Ping.class, Packet20002Ping.CONVERTER),
                new Info(20003, Packet20003Pong.class, Packet20003Pong.CONVERTER),
                new Info(20004, Packet20004Login.class, Packet20004Login.CONVERTER),
                new Info(20005, Packet20005Handshake.class, Packet20005Handshake.CONVERTER),
                new Info(20006, Packet20006CrashReport.class, Packet20006CrashReport.CONVERTER),
                new Info(20007, Packet20007DataFixerLogs.class, Packet20007DataFixerLogs.CONVERTER),
                new Info(20008, Packet20008UIOpen.class, Packet20008UIOpen.CONVERTER),
                new Info(20009, Packet20009UIClosed.class, Packet20009UIClosed.CONVERTER),
                new Info(20010, Packet20010NotifyDebugUser.class, Packet20010NotifyDebugUser.CODEC)
        });
    }
}
