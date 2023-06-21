package com.fazziclay.opentoday.telemetry;

import com.fazziclay.neosocket.Client;
import com.fazziclay.neosocket.PacketHandler;
import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.opentoday.telemetry.packet.*;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class TelemetryServerHandler implements PacketHandler {
    private final Client client;
    private int clientVersion = 0;
    private UUID instanceId;
    private boolean debugBuild = false;
    private int appVersion = 0;
    private long lastActivity;
    private Timer afkTimer = new Timer();

    public TelemetryServerHandler(Client client) {
        this.client = client;
        logActivity();
    }

    @Override
    public void setup(Client client) {
        logActivity();
    }

    @Override
    public void preDisconnect(Client client) {
        log("Pre disconnect");
    }

    @Override
    public void fatalException(Client client, Exception e) {
        log("FatalException: " + e);
    }

    @Override
    public void received(Client client, Packet packet) {
        logActivity();

        try {
            if (packet instanceof PacketSetVersion) {
                PacketSetVersion p = (PacketSetVersion) packet;
                clientVersion = p.getVersion();
                if (clientVersion < 2) {
                    throw new RuntimeException("Unknown version!");
                }

            } else if (clientVersion == 0) {
                client.send(new PacketVersionUndefined());
                client.shutdown();

            } else if (clientVersion >= 2 && packet instanceof Packet20002Ping) {
                Packet20002Ping p = (Packet20002Ping) packet;
                client.send(new Packet20003Pong(p.getMessage()));

            } else if (clientVersion >= 2 && packet instanceof Packet20003Pong) {
                Packet20003Pong p = (Packet20003Pong) packet;
                log("Received pong: " + p.getMessage());

            } else if (clientVersion >= 2 && packet instanceof Packet20004Login) {
                this.instanceId = ((Packet20004Login) packet).getInstanceId();

            } else if (instanceId == null) {
                client.send(new Packet20003Pong("You not logged! Send login packet before"));
                log("Not logged: Disconnect");
                client.shutdown();

            } else if (packet instanceof Packet20005Handshake) {
                Packet20005Handshake h = (Packet20005Handshake) packet;
                appVersion = h.getAppVersion();

            } else if (packet instanceof Packet20010NotifyDebugUser) {
                debugBuild = true;

            } else if (clientVersion >= 2 && packet instanceof Packet20006CrashReport) {
                log("Crash");
                Packet20006CrashReport c = (Packet20006CrashReport) packet;
                new Thread(() -> TelemetryServer.crash(debugBuild, instanceId, client.getAddress(), clientVersion, appVersion, c.getCrashId(), c.getThrowable(), c.getCrashReport())).start();

            } else if (clientVersion >= 2 && packet instanceof Packet20007DataFixerLogs) {
                log("DataFixer");
                Packet20007DataFixerLogs d = (Packet20007DataFixerLogs) packet;
                new Thread(() -> TelemetryServer.dataFixer(debugBuild, instanceId, client.getAddress(), clientVersion, appVersion, d.getDataVersion(), d.getLogs())).start();

            } else if (clientVersion >= 2 && packet instanceof Packet20008UIOpen) {
                new Thread(() -> TelemetryServer.uiOpened(debugBuild, instanceId, client.getAddress(), clientVersion, appVersion)).start();

            } else if (clientVersion >= 2 && packet instanceof Packet20009UIClosed) {
                new Thread(() -> TelemetryServer.uiClosed(debugBuild, instanceId, client.getAddress(), clientVersion, appVersion)).start();

            } else {
                log("Unknown packet " + packet);
            }

        } catch (Exception e) {
            log("Exception " + e);
            e.printStackTrace();
            log("Disconnect Reason: Exception!");
            try {
                client.send(new Packet20002Ping("Disconnect: Exception in server while packet parse: " + e));
            } catch (IOException ignored) {}
            client.shutdown();
        }
    }

    private void logActivity() {
        lastActivity = System.currentTimeMillis();
        afkTimer.cancel();
        afkTimer = new Timer();
        afkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (System.currentTimeMillis() - lastActivity > 10 * 60) {
                    try {
                        client.send(new Packet20002Ping("AFK > 10 seconds\nDisconnect"));
                    } catch (IOException ignored) {}
                    client.shutdown();
                }
            }
        }, 11 * 1000);
    }

    private void log(String s) {
        System.out.printf("[%s] [cv%s av%s %s %s] %s%n", TelemetryServer.getDateTime(), clientVersion, appVersion, client.getAddress(), instanceId, s);
    }
}
