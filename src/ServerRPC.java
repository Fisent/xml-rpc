import org.apache.xmlrpc.WebServer;

public class ServerRPC {

    private int stan_konta;

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
        System.out.println("Message received: " + message);
        return true;
    }
}
