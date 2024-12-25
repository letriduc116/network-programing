package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//remote interface --> extends Remote
public interface  ISearch extends Remote { // bat buoc phai extends Remote
    public boolean checkUserName(String userName) throws RemoteException; // bat buoc phai throws RemoteException
    public int login(String userName, String password) throws RemoteException;
    public List<Student> findById(int sessionId ,int id) throws RemoteException;
    public List<Student> findByName(int sessionId ,String name) throws RemoteException;
    public void logout(int sessionId) throws RemoteException;
}
