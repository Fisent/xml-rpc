import java.util.Vector;

public class Envelope {

    public String message;
    public int senderPort;
    public int recieverPort;
    public int youAre;
    public Vector<Object> arguments;

    public Envelope(String message, int sender, int reciever, int youAre, Vector<Object> arguments){
        this.message = message;
        this.senderPort = sender;
        this.recieverPort = reciever;
        this.youAre = youAre;
        this.arguments = arguments;
    }

    public Envelope(String encodedEnvelope){
        String[] tab = encodedEnvelope.split(":");
        this.message = tab[0];
        this.senderPort = Integer.parseInt(tab[1]);
        this.recieverPort = Integer.parseInt(tab[2]);
        this.youAre = Integer.parseInt(tab[3]);
    }

    @Override
    public String toString(){
        return message + ':' + senderPort + ':' + recieverPort + ':' + youAre;
    }
}
