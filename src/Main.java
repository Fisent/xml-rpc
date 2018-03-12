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
        String message = "";
        while(!message.equals("exit")){
            message = getString(">", scanner);
            switch (message){
                case "help":
                    System.out.println("help");
                    break;
                case "port":
                    System.out.println(server.port);
                    break;
                case "list":
                    System.out.println(server.clients);
                    break;
                case "message":
                    int port = getInteger("Wpisz port odbiorcy", scanner);
                    Vector<Object> arguments = new Vector<>();
                    arguments.add(getString("Wpisz wiadomosc: ", scanner));
                    server.getClient(port).client.execute("mojserwer.message", arguments);
            }
        }
    }

    public static void main(String[] args) throws XmlRpcException, IOException {
        Scanner scanner = new Scanner(System.in);

        ServerWrapper server = new ServerWrapper(getInteger("Podaj numer portu: ", scanner));
        console(scanner, server);
    }
}
