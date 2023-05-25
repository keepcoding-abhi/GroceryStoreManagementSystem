/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;
import communicate.GetUserInfo;
import login.UserProfile;
import login.LogInCredentials;
import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import javax.swing.JOptionPane;
import communicate.FeedbackInfo;
import communicate.Payment;
import communicate.Order;
/**
 *
 * @author abhi
 */
public class ReqClient implements Runnable{
    private Socket client;
    private DBAccess1 dba;
    private ObjectOutputStream out;
    private ObjectInputStream oin;
    public ReqClient(Socket client,DBAccess1 dba){
            this.dba = dba;
            this.client= client;
            Thread t = new Thread(this);
            t.start();
    }
    
    public void run(){
        try{
            oin = new ObjectInputStream(this.client.getInputStream());
            out = new ObjectOutputStream(this.client.getOutputStream());
            Object obj = oin.readObject();
            if(obj instanceof LogInCredentials){
                LogInCredentials lic = (LogInCredentials)obj;
                dba.checkUsrPass(lic);
                out.writeObject(lic);
                out.flush();
            }
            else if(obj instanceof UserProfile){
                UserProfile newUser = (UserProfile)obj;
                dba.addAccount(newUser);
                out.writeObject("Account Created Successfully.");
                out.flush();
            }
            else if(obj instanceof GetUserInfo){
                Object send = dba.getUserProfile(((GetUserInfo)obj).getLogInCredentials());
                out.writeObject(send);
                out.flush();
            }
            else if(obj instanceof FeedbackInfo){
                Object send = dba.addFeedback((FeedbackInfo)obj);
                out.writeObject(send);
                out.flush();
            }
            else if(obj instanceof Order){
                Object send = dba.scanOrder((Order)obj);
                out.writeObject(send);
                out.flush();
            }
            else if(obj instanceof Payment){
                Object send = dba.pay((Payment)obj);
                out.writeObject(send);
                out.flush();
            }
            oin.close();
            out.close();
        }
        catch(IOException | ClassNotFoundException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
