import java.util.Vector;

public class Envelope {

    public String message;
    public int senderPort;
    public int recieverPort;
    public int youAre;
    public boolean isResponse;

    public Envelope(String message, int sender, int reciever, int youAre, boolean isResponse){
        this.message = message;
        this.senderPort = sender;
        this.recieverPort = reciever;
        this.youAre = youAre;
        this.isResponse = isResponse;
    }

    public Envelope(String encodedEnvelope){
        String[] tab = encodedEnvelope.split(":");
        this.message = tab[0];
        this.senderPort = Integer.parseInt(tab[1]);
        this.recieverPort = Integer.parseInt(tab[2]);
        this.youAre = Integer.parseInt(tab[3]);
        this.isResponse = Boolean.parseBoolean(tab[4]);
    }

    public String encode(){
        return message + ':' + senderPort + ':' + recieverPort + ':' + youAre + ":" + isResponse;
    }

    @Override
    public String toString(){
        return "Wiadomosc: " + message + ", od:" + senderPort + ", do:" + recieverPort + "(nastepny:" + youAre + ", odpowiedz:" + isResponse + ")";
    }
}
