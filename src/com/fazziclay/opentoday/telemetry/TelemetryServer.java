package com.fazziclay.opentoday.telemetry;

import ru.fazziclay.javaneoutil.FileUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.UUID;

public class TelemetryServer {
    public static void crash(boolean debugBuild, UUID instanceId, String addr, int cliVer, int appVer, UUID crashId, String throwable, String crashReport) {
        FileUtil.setText(new File(getUserFolder(instanceId), getDateTime() + "-crash"), crashReport);
        FileUtil.setText(new File("./received/v2"+(debugBuild ? "/debug" : "")+"/crashes/" + getDateTime() + "-" + crashId.toString()), crashReport);
        logToFile(instanceId, debugBuild, addr, cliVer, appVer, "@Crash " + crashId + ": " + throwable);
    }

    public static void dataFixer(boolean debugBuild, UUID instanceId, String addr, int cliVer, int appVer, int dataVersion, String logs) {
        FileUtil.setText(new File(getUserFolder(instanceId), getDateTime() + "-datafixer"), logs);
        FileUtil.setText(new File("./received/v2"+(debugBuild ? "/debug" : "")+"/datafixes/" + getDateTime() + "-" + instanceId), logs);
        logToFile(instanceId, debugBuild, addr, cliVer, appVer, "@DataFixer upgraded to dataVersion=" + dataVersion);
    }

    public static void uiOpened(boolean debugBuild, UUID instanceId, String addr, int cliVer, int appVer) {
        logToFile(instanceId, debugBuild, addr, cliVer, appVer, "@UIOpen");
    }

    public static void uiClosed(boolean debugBuild, UUID instanceId, String addr, int cliVer, int appVer) {
        logToFile(instanceId, debugBuild, addr, cliVer, appVer, "@UIClosed");
    }

    private static void logToFile(UUID instanceId, boolean debug, String address, int cV, int aV, String text) {
        FileUtil.addText(new File("./received/v2/logs.txt"), String.format("%s[%s] [%s - %s] av%s cv%s %s", (debug ? "[DEBUG_BUILD] " : ""), getDateTime(), instanceId, address, cV, aV, text) + "\n");
    }

    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH_mm_ss", Locale.getDefault());
        return dateFormat.format(new GregorianCalendar().getTime());
    }

    private static String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH_mm_ss", Locale.getDefault());
        return dateFormat.format(new GregorianCalendar().getTime());
    }

    private static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());
        return dateFormat.format(new GregorianCalendar().getTime());
    }

    private static File getUserFolder(UUID instanceId) {
        return new File("./received/v2/users/" + instanceId.toString() + "/");
    }
}
