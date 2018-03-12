public class Envelope {

    public String message;
    public int senderPort;
    public int recieverPort;

    public Envelope(String message, int sender, int reciever){
        this.message = message;
        this.senderPort = sender;
        this.recieverPort = reciever;
    }
}
