package StudentInfomation;

import java.util.HashMap;
import java.util.Map;

public class UserAuth {
    private Map<String, String> credentials;

    public UserAuth() {
        credentials = new HashMap<>();
        credentials.put("admin", "12345");
        credentials.put("user", "password");
    }

    public boolean validate(String username, String password) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}
