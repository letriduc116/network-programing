package RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//remote class --> implement interface
public class SearchImplement extends UnicastRemoteObject implements ISearch {
    Dao dao ; // dung 1 ket noi duy nhat xuong database
    private static int USER_NO = 0;
    Set<Integer> sessions; // luu lai cac session da login de verify
    public SearchImplement() throws RemoteException, SQLException, ClassNotFoundException {
        super();
        this.dao = new Dao();
        this.sessions = new HashSet<>();
    }

    @Override
    public boolean checkUserName(String userName) throws RemoteException {
        try { // bat buoc phai try catch do remote class chi nhan RemoteException
            return dao.checkUserName(userName);
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public int login(String userName, String password) throws RemoteException {
        try {// bat buoc phai try catch do remote class chi nhan RemoteException
            boolean res = dao.login(userName, password);
            if (!res) return -1; // login fail
            sessions.add(USER_NO); // luu lai session id da active
            return USER_NO++;
        } catch (SQLException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public List<Student> findById(int sessionId, int id) throws RemoteException {
        boolean isLogin = sessions.contains(sessionId);// kiem tra session id da login chua (duoc luu trong sessions)
        if (!isLogin) return null;
        try {// bat buoc phai try catch do remote class chi nhan RemoteException
            return dao.findById(id);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public List<Student> findByName(int sessionId, String name) throws RemoteException{
        boolean isLogin = sessions.contains(sessionId);
        if (!isLogin) return null;
        try {// bat buoc phai try catch do remote class chi nhan RemoteException
            return dao.findByName(name);
        } catch (Exception e) {
            throw new RemoteException(e.getMessage());
        }
    }

    @Override
    public void logout(int sessionId) throws RemoteException {
        sessions.remove(sessionId); // xoa session id da logout
    }
}
