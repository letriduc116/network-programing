package RMI;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception {
        Registry reg = LocateRegistry.createRegistry(2000); // tao registry tren port 2000 (dang ki port)
        reg.rebind("SEARCH", new SearchImplement()); // dang ki remote object (SearchImplement) voi ten "SEARCH"
    }
}
