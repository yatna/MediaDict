package test.com.carwash;

/**
 * Created by DELL on 6/3/2015.
 */
public class User {

    String name, username, password,address;
    String phone;
    int id;

    public User(int id,String name, String  phone, String username, String password,String address) {
        this.name = name;
        this.id=id;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.address=address;
    }

    public User(String username, String password) {
        this(-1,"","", username, password,"");
    }
}
