import org.apache.xmlrpc.XmlRpcException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Main {

    public static int getInteger(String message, Scanner scanner){
        System.out.print(message + ": ");
        return Integer.parseInt(scanner.nextLine());
    }

    public static String getString(String message, Scanner scanner){
        System.out.print(message + ": ");
        return scanner.nextLine();
    }

    public static void console(Scanner scanner, ServerWrapper server) throws XmlRpcException, IOException {
        String command = "";
        while(!command.equals("exit")){
            command = getString(">", scanner);
            switch (command){
                case "help":
                    System.out.println("help");
                    break;
                case "port":
                    System.out.println(server.port);
                    break;
                case "list":
                    System.out.println("lewy: " + server.leftClient + ", prawy: " + server.rightClient);
                    break;
                case "message":
                    int port = getInteger("Wpisz port odbiorcy", scanner);
                    Vector<Object> arguments = new Vector<>();
                    String message = getString("Wpisz wiadomosc: ", scanner);
                    Envelope envelope = new Envelope(message, server.port, port, server.thisClient.port, false);
                    arguments.add(envelope.toString());
                    server.thisClient.client.execute("mojserwer.message", arguments);
            }
        }
    }

    public static void main(String[] args) throws XmlRpcException, IOException {
        Scanner scanner = new Scanner(System.in);

        ServerWrapper server = new ServerWrapper(getInteger("Podaj numer portu: ", scanner));
        console(scanner, server);
    }
}
