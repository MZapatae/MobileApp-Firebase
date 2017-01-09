package cl.mzapatae.mobileFirebase.objets;

/**
 * @author Miguel A. Zapata - MZapatae
 * @version 1.0
 *          Created on: 26-12-16
 *          E-mail: miguel.zapatae@gmail.com
 */

public class User {
    private String uid;
    private String email;
    private String avatar;
    private String firstName;
    private String lastName;
    private String userName;

    public User() {
        // Default constructor required for calls to DataSnapshot.getvalue
    }

    public User(String uid, String email, String name, String lastname, String username, String avatar) {
        this.uid = uid;
        this.email = email;
        this.firstName = name;
        this.lastName = lastname;
        this.userName = username;
        this.avatar = avatar;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }
}
