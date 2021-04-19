package Server;

import spark.Spark;
import spark.debug.DebugScreen;

public class Server {
    public static void main(String[] args) {
        Bootstrap bs = new Bootstrap();
        bs.init();
        Spark.port(9000);
        DebugScreen.enableDebugScreen();
        Router.configure();
    }
}
