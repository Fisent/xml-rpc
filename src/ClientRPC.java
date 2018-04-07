import org.apache.xmlrpc.XmlRpcClient;

import java.util.ArrayList;

public class ClientRPC{

    public XmlRpcClient client;
    public int port;

    public ClientRPC(int port) {
        this.port = port + 8000;
        try {
            client = new XmlRpcClient("http://localhost:" + this.port);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public String toString(){
        return "Client[" + port + "]";
    }
}
