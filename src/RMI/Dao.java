package RMI;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dao {
    Connection con;

    public Dao() throws ClassNotFoundException, SQLException {
        String driver = "net.ucanaccess.jdbc.UcanaccessDriver";
        String dbFile = "G:\\Java\\network_programing\\StList.accdb";
        String url = "jdbc:ucanaccess://" + dbFile;
        Class.forName(driver);
        this.con = DriverManager.getConnection(url);
    }

    public void closeDb() throws SQLException {
        if (con != null) {
            con.close();
        }
    }

    public boolean checkUserName(String userName) throws SQLException {
        String sql = "select * from users where username = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public boolean login(String userName, String password) throws SQLException {
        String sql = "select * from users where username = ? and password = ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, userName);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    public List<Student> findById(int id) {
        List<Student> list = new ArrayList<>();
        String sql = "select * from students where stid = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("stid"));
                st.setName(rs.getString("stName"));
                st.setGpa(rs.getDouble("stGrade"));
                list.add(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> findByName(String name) {
        List<Student> list = new ArrayList<>();
        String sql = "select * from students where stName like ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, "%" + name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student st = new Student();
                st.setId(rs.getInt("stid"));
                st.setName(rs.getString("stName"));
                st.setGpa(rs.getDouble("stGrade"));
                list.add(st);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
