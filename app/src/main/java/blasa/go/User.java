package blasa.go;

/**
 * Created by omarelamri on 04/04/2018.
 */

public class User {

    private String id;
    private String name;
    private String phoneNumber;
    private String email;
    private String password;
    private String cartype;
    private String gender;

    public User() {
    }

    public User(String id, String name, String phoneNumber, String email, String password, String cartype, String gender) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.cartype = cartype;
        this.gender = gender;
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

    public void setCartype(String cartype){
        this.cartype = cartype;
    }

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getCartype(){
        return cartype;
    }

    public String getGender(){
        return gender;
    }

}