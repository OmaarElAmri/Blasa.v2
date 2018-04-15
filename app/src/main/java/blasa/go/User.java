package blasa.go;

/**
 * Created by omarelamri on 04/04/2018.
 */

public class User {
    private String id;
    private String name;
    private String email;
    private String photoURL;
    private String password;


    public User() {
        this.photoURL="";
    }

    public User(String id, String name, String email, String password,String photoURL) {

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.photoURL = photoURL;


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getphotoURL (){return photoURL;}

    public void setphotoURL (String photoURL){this.photoURL = photoURL;}
}