import org.apache.xmlrpc.WebServer;

import java.util.ArrayList;

public class ServerWrapper {

    public WebServer webServer;
    public ArrayList<ClientRPC> clients;
    public int port;

    public ServerWrapper(int port){
        webServer = new WebServer(port);
        clients = new ArrayList<ClientRPC>();

        ArrayList<Integer> set = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            set.add(8000 + i);
        }
        set.remove(set.indexOf(port));
        for(int portNumber : set){
            clients.add(new ClientRPC(portNumber));
        }
        this.port = port;
        webServer.addHandler("mojserwer", new ServerRPC());
        System.out.println("Serwer wystartował pomyślnie");
        System.out.println("Nasłuchuje na porcie: " + port);
        System.out.println("Aby zatrzymać serwer naciśnij ctrl + c");
        webServer.start();
    }

    public ClientRPC getClient(int port){
        for(ClientRPC client : clients){
            if(client.port == port)
                return client;
        }
        return null;
    }

    public String toString(){
        return "Serwer " + port;
    }
}
