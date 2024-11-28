package TH9;

import java.util.ArrayList;
import java.util.List;

public class Dao {
    List<Student> ls ;
    List<User> lu ;
    public Dao() {
        initData() ;
    }

    private void initData() {
        ls = new ArrayList<Student>();
        ls.add(new Student(1,"le tri duc",9.5));
        ls.add(new Student(2,"vo xuan dong",8.5));
        ls.add(new Student(3,"tran nhut anh",8));
        ls.add(new Student(4,"dao duy nhat",7.5));

        lu = new ArrayList<User>();
        lu.add(new User("admin", "12345"));
        lu.add(new User("triduc", "23456"));
    }


    public boolean checkUserName(String userName) {
        for (User user : lu) {
            if (user.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    public boolean login(String userName, String password) {
       for (User user : lu) {
           if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
               return true;
           }
       }
       return false;
    }

    public List<Student> findById(int id) {
        List<Student> list = new ArrayList<>();
        for (Student s : ls) {
            if (s.getId() == id) {
                list.add(s);
            }
        }
        return list;
    }
}
