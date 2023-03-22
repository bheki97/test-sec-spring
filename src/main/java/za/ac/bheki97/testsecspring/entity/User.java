package za.ac.bheki97.testsecspring.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="user_tbl")
public class User implements Serializable {

    @Id
    @Column(name = "identity_num")
    private String idNumber;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Character gender;
    private String mobileNumber;

    public User(){

    }

    public User(User user) {

    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String id) {
        this.idNumber = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }



    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
