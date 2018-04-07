import org.apache.xmlrpc.WebServer;

import java.util.ArrayList;

public class ServerWrapper {
    public static ArrayList<ServerWrapper> servers = new ArrayList<>();

    public WebServer webServer;
    public ClientRPC leftClient;
    public ClientRPC rightClient;
    public ClientRPC thisClient;
    public int port;

    public ServerWrapper(int port){
        webServer = new WebServer(port);
        leftClient = new ClientRPC((port - 1) % 5);
        rightClient = new ClientRPC((port + 1) % 5);
        thisClient = new ClientRPC(port % 5);

        this.port = port;
        webServer.addHandler("mojserwer", new ServerRPC());
        System.out.println("Serwer wystartował pomyślnie");
        System.out.println("Nasłuchuje na porcie: " + port);
        System.out.println("Aby zatrzymać serwer naciśnij ctrl + c");
        webServer.start();
        ServerWrapper.servers.add(this);
    }

    public String toString(){
        return "Serwer " + port;
    }

    public static ServerWrapper getServer(int port){
        for(ServerWrapper server : ServerWrapper.servers){
            if(server.port == port)
                return server;
        }
        return null;
    }
}
