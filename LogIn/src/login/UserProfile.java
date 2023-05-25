/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import javax.swing.ImageIcon;
import java.io.Serializable;
import javax.swing.*;

/**
 *
 * @author abhi
 */
public class UserProfile implements Serializable{
    private ImageIcon profilePhoto,signature;
    private String firstName,middleName,lastName;
    private String country,mobile,email;
    private String userName,passWord;

    public String getUserName(){
        return userName;
    }
    public String getPass(){
        return passWord;
    }
    public String getMobile(){
        return mobile;
    }
    public String getCountry(){
        return country;
    }
    public String getEmail(){
        return email;
    }
    public String getFirstName(){
        return firstName;
    }
    public String getMiddleName(){
        return middleName;
    }
    public String getLastName(){
        return lastName;
    }
    public ImageIcon getSign(){
        return signature;
    }
    public ImageIcon getProfile(){
        return profilePhoto;
    }
    public void setLogin(String user,String pass){
        userName = user;
        passWord = pass;
    }
    public void setContact(String country,String mobile,String email){
        this.country = country;
        this.mobile = mobile;
        this.email = email;
    }
    public void setName(String first,String middle,String last){
        firstName = first;
        middleName = middle;
        lastName = last;
    }
    public void setPhotos(ImageIcon prof,ImageIcon sign){
        profilePhoto = prof;
        signature = sign;
    }
}
