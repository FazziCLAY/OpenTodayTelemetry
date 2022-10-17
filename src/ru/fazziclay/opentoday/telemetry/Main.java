package ru.fazziclay.opentoday.telemetry;

import com.fazziclay.neosocket.NeoSocket;
import com.fazziclay.neosocket.Server;
import ru.fazziclay.javaneoutil.JavaNeoUtil;

public class Main {

    public static void main(String[] args) {
        if (args.length == 0) {
            printHelp();
            return;
        }

        if (args[0].equalsIgnoreCase("--help")) {
            printHelp();
            return;
        }

        if (args[0].equalsIgnoreCase("--server")) {
            if (args.length < 2) {
                throw new RuntimeException("Port is not set!");
            }
            int port;
            try {
                port = Integer.parseInt(args[1]);
            } catch (Exception e) {
                throw new RuntimeException("Port parse exception", e);
            }
            runServer(port);
            return;
        }


        if (args[0].equalsIgnoreCase("--viewer")) {
            runViewer();
            return;
        }

        System.out.println("Unknown arguments! Use --help for help");
    }

    private static void printHelp() {
        System.out.println("== OpenTodayTelemetry ==" +
                "\n * --help - help for args" +
                "\n * --server <port> - run server in port" +
                "\n * --viewer - viewer mode");
    }

    private static void runServer(int port) {
        System.out.println("Server starting...");
        System.out.println("OpenTodayTelemetry v2\nBy: FazziCLAY");
        System.out.println("Check libs version. If throw: add to classPath JavaNeoUtil.jar ? NeoSocket.jar");
        System.out.printf("Libs:\n * JavaNeoUtil: %s\n * NeoSocket: %s%n", JavaNeoUtil.VERSION_NAME, NeoSocket.VERSION_NAME);
        Server server = new Server(port, new TelemetryPackets(), TelemetryServerHandler::new, System.out);
        Thread serverThread = new Thread(server::run);
        serverThread.setName("ServerThread");
        serverThread.start();
        while (server.isBusy()) {
            server.tick();
        }
        System.out.println("Server stopped!");
    }

    private static void runViewer() {
        System.out.println("In dev...");
    }
}
