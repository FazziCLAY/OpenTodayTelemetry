package ru.fazziclay.opentoday.telemetry.packet;

import com.fazziclay.neosocket.packet.Packet;
import com.fazziclay.neosocket.packet.PacketConverter;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class Packet20006CrashReport implements Packet {
    public static Converter CONVERTER = new Converter();
    private static class Converter extends PacketConverter {
        @Override
        public Packet decode(byte[] data) {
            JSONObject j = new JSONObject(new String(data, StandardCharsets.UTF_8));
            UUID crashId = UUID.fromString(j.getString("crashId"));
            String throwable = j.getString("throwable");
            String crashReport = j.getString("crashReport");

            if (throwable == null || crashReport == null) {
                throw new NullPointerException("CrashReportPacket fields contain null!");
            }
            return new Packet20006CrashReport(crashId, throwable, crashReport);
        }

        @Override
        public byte[] encode(Packet packet) {
            Packet20006CrashReport p = (Packet20006CrashReport) packet;
            if (p.crashId == null || p.throwable == null || p.crashReport == null) {
                throw new NullPointerException("CrashReportPacket fields contain null!");
            }

            JSONObject j = new JSONObject();
            j.put("crashId", p.crashId.toString());
            j.put("throwable", p.throwable);
            j.put("crashReport", p.crashReport);

            return j.toString().getBytes(StandardCharsets.UTF_8);
        }
    }

    private final UUID crashId;
    private final String throwable;
    private final String crashReport;

    public Packet20006CrashReport(UUID crashId, String throwable, String crashReport) {
        this.crashId = crashId;
        this.throwable = throwable;
        this.crashReport = crashReport;
    }

    public UUID getCrashId() {
        return crashId;
    }

    public String getThrowable() {
        return throwable;
    }

    public String getCrashReport() {
        return crashReport;
    }

    @Override
    public String toString() {
        return "CrashReportPacket{" +
                "crashId=" + crashId +
                ", throwable='" + throwable + '\'' +
                ", crashReport='" + (crashReport.length() > 100 ? crashReport.substring(0, 97) + "..." : crashReport) + '\'' +
                "} " + super.toString();
    }
}
