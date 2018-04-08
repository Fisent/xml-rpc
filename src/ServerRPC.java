import org.apache.xmlrpc.WebServer;

import java.util.Random;
import java.util.Vector;

public class ServerRPC {

    public static final double K = 0.1;

    private int stan_konta;

    public int port;

    public int stan(){
        return stan_konta;
    }

    public int wyplac(int suma){
        if(suma <= stan_konta){
            stan_konta -= suma;
            return suma;
        } else {
            int wyplacono = stan_konta;
            stan_konta = 0;
            return wyplacono;
        }
    }

    public int wplac(int suma){
        stan_konta += suma;
        return suma;
    }

    public String show(){
        String result = "Dostepne metody: \n";
        result += "show():String\n";
        result += "stan():int - wyświetla stan konta\n";
        result += "wplac(int):int - wplac pieniądze na konto\n";
        result += "wyplac(int):int - wypłać pieniądze z konta\n";
        result += "przelew(int, String):boolean - wykonaj przelew podanej sumy na podany rachunek\n";
        return result;
    }

    public boolean przelew(int suma, String nr_rachunku){
        try{
            System.out.println("Wykonuję przelew na rachunek: " + nr_rachunku);
            Thread.sleep(5000);
            if (suma <= stan_konta){
                stan_konta -= suma;
                return true;
            } else
                return false;
        } catch(InterruptedException ex){
            ex.printStackTrace();
            Thread.currentThread().interrupt();
        }
        return false;
    }

    private String pingPong(String message){
        System.out.println(message);
        double random = (new Random()).nextDouble();
        if(message.equals("CUT_SHOT")){
            return null;
        }
        else if(message.equals("PING"))
            return random > ServerRPC.K ? "PONG" : "CUT_SHOT";
        else if(message.equals("PONG"))
            return random > ServerRPC.K ? "PING" : "CUT_SHOT";
        return null;
    }

    public boolean message(String message){
        Envelope envelope = new Envelope(message);
        this.port = envelope.youAre;
        ServerWrapper thisServer = ServerWrapper.getServer(this.port);
        if(this.port == envelope.recieverPort && !envelope.isResponse) {
            System.out.println("Message received: " + envelope + " od serwera: " + envelope.senderPort);
            ClientRPC client;
            if(envelope.senderPort > this.port)
                client = thisServer.rightClient;
            else
                client = thisServer.leftClient;

            Envelope response;

            String pingPongMessage = pingPong(envelope.message);
            if((envelope.message.equals("PING") || envelope.message.equals("PONG") || envelope.message.equals("CUT_SHOT")) && pingPongMessage != null){
                response = new Envelope(pingPongMessage, envelope.recieverPort, envelope.senderPort, client.port, false);
            }
            else
                response = new Envelope( envelope.message, envelope.recieverPort, envelope.senderPort, client.port, true);
            Vector<Object> arguments = new Vector<>();
            arguments.add(response.encode());
            try {
                client.client.execute("mojserwer.message", arguments);
            } catch (Exception e){
                e.printStackTrace();
            }
        } else if(envelope.isResponse && this.port == envelope.recieverPort){
            System.out.println("Response recieved, message: " + envelope.message + " delivered");
        }
        else {
            if(this.port > envelope.recieverPort){
                envelope = new Envelope(envelope.message, envelope.senderPort, envelope.recieverPort, thisServer.leftClient.port, envelope.isResponse);
                Vector<Object> arguments = new Vector<>();
                arguments.add(envelope.encode());
                try {
                    thisServer.leftClient.client.execute("mojserwer.message", arguments);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("Forwarded the message: " + envelope.message + " to server: " + envelope.youAre);
            }
            else{
                envelope = new Envelope(envelope.message, envelope.senderPort, envelope.recieverPort, thisServer.rightClient.port, envelope.isResponse);
                Vector<Object> arguments = new Vector<>();
                arguments.add(envelope.encode());
                try {
                    thisServer.rightClient.client.execute("mojserwer.message", arguments);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("Forwarded the message: " + envelope.message + " to server: " + envelope.youAre);
            }
        }
        return true;
    }
}
