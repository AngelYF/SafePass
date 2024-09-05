package por.ayf.eng.sp.database.bean;

/**
 * Object contains the data of a user of database.
 *
 * @author: Ángel Yagüe Flor.
 * @version: 2.0.
 */

public class BeanUser {
    private String username;
    private String password;

    public BeanUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

