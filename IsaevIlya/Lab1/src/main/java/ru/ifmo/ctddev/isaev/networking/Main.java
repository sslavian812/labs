package ru.ifmo.ctddev.isaev.networking;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Ilya Isaev
 */
public class Main {

    public static final int PACKET_LENGTH = 128;
    public static final int SLEEP_TIME = 5000;
    public static final Map<String, BroadcasterInfo> broadcasters = new TreeMap<>(String::compareTo);
    public static final Map<String, Message> pendingMessages = new HashMap<>();
    private static final Executor executor = Executors.newFixedThreadPool(3);
    public static int SENDER_PORT = 4445;
    public static int RECEIVER_PORT;
    public static String HOSTNAME;

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Expected two arguments: <hostname> [<port>]?");
            return;
        }
        if (args.length > 1) {
            SENDER_PORT = Integer.parseInt(args[1]);
        }
        RECEIVER_PORT = SENDER_PORT + 1;
        HOSTNAME = args[0];
        executor.execute(new Broadcaster());
        executor.execute(new Receiver());
        executor.execute(new Printer());
    }
}
