/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;
import java.io.Serializable;

/**
 *
 * @author abhi
 */
public class LogInCredentials implements Serializable{
    private String username,password;
    private boolean usr,pass;
    public void setUserName(String usr){
        username = usr;
    }
    public void setPassword(String pass){
        password = pass;
    }
    public String getUserName(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public void foundUser(boolean usr){
        this.usr = usr;
    }
    public void foundPass(boolean pass){
        this.pass = pass;
    }
    public boolean checkUser(){
        return usr;
    }
    public boolean checkPass(){
        return pass;
    }
}
