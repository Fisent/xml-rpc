import org.apache.xmlrpc.WebServer;

import java.util.Vector;

public class ServerRPC {

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

    public boolean message(String message){
        Envelope envelope = new Envelope(message);
        this.port = envelope.youAre;
        ServerWrapper thisServer = ServerWrapper.getServer(this.port);
        if(this.port == envelope.recieverPort) {
            System.out.println("Message received: " + envelope + " od serwera: " + envelope.senderPort);

            Envelope response = new Envelope("Otrzymano wiadomosc " + envelope.message, envelope.recieverPort, envelope.senderPort, envelope.senderPort, null);
            Vector<Object> arguments = new Vector<>();
            arguments.add(response.toString());
            ClientRPC client = new ClientRPC(envelope.senderPort - 8000);
            try {
                client.client.execute("mojserwer.message", arguments);
            } catch (Exception e){
                e.printStackTrace();
            }

            if(envelope.message.equals("przelew") || envelope.message.equals("show") || envelope.message.equals("wplac") || envelope.message.equals("wyplac")){
                try {
                    thisServer.thisClient.client.execute("mojserwer." + envelope.message, arguments);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{
            if(this.port > envelope.recieverPort){
                envelope = new Envelope(envelope.message, envelope.senderPort, envelope.recieverPort, thisServer.leftClient.port, null);
                Vector<Object> arguments = new Vector<>();
                arguments.add(envelope.toString());
                try {
                    thisServer.leftClient.client.execute("mojserwer.message", arguments);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                System.out.println("Forwarded the message: " + envelope.message + " to server: " + envelope.youAre);
            }
            else{
                envelope = new Envelope(envelope.message, envelope.senderPort, envelope.recieverPort, thisServer.rightClient.port, null);
                Vector<Object> arguments = new Vector<>();
                arguments.add(envelope.toString());
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
