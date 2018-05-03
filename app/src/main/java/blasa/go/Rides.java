package blasa.go;

/**
 * Created by omarelamri on 25/04/2018.
 */

public class Rides {
    private String start;
    private String finish;
    private String date;
    private String time;
    private String price;
    private String phone;
    private String name;
    private String photoURL;
    private String opt1;
    private String opt2;
    private String opt3;
    private String userid;

    public  Rides(){

    }

    public Rides(String start, String finish, String date, String time, String price, String phone, String name, String photoURL, String opt1, String opt2, String opt3, String userid) {
        this.start = start;
        this.finish = finish;
        this.date = date;
        this.time = time;
        this.price = price;
        this.phone = phone;
        this.name = name;
        this.photoURL = photoURL;
        this.opt1 = opt1;
        this.opt2 = opt2;
        this.opt3 = opt3;
        this.userid = userid;

    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getStart() {
        return start;
    }

    public String getFinish() {
        return finish;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPrice() {
        return price;
    }

    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public String getOpt1() {
        return opt1;
    }

    public void setOpt1(String opt1) {
        this.opt1 = opt1;
    }

    public String getOpt2() {
        return opt2;
    }

    public void setOpt2(String opt2) {
        this.opt2 = opt2;
    }

    public String getOpt3() {
        return opt3;
    }

    public void setOpt3(String opt3) {
        this.opt3 = opt3;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }


}
