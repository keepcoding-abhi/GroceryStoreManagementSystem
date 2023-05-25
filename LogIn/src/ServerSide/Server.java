/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;
import java.net.ServerSocket;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.net.Socket;
/**
 *
 * @author abhi
 */
public class Server implements Runnable{
    private ServerSocket server;
    private boolean acceptStatus;
    private DBAccess1 dba;
    public Server(DBAccess1 pass){
        dba = pass;
        acceptStatus = true;
        try{
            server = new ServerSocket(3173);
            Thread t = new Thread(this);
            t.start();
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null,ioe,"Error",JOptionPane.ERROR_MESSAGE);
        }   
    }
    
    public void run(){
        System.out.println("Entered");
        acceptConnections();
        System.out.println("Exiting");
    }
    
    private void acceptConnections(){
        try{
            System.out.println("Entered1");
            while(true){
                Socket client = server.accept();
                new ReqClient(client,dba);
                if(acceptStatus == false)
                    break;
            }
            System.out.println("Exited 1");
        }
        catch(IOException ioe){
            JOptionPane.showMessageDialog(null,ioe,"Error",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String args[]){
        //new Server();
    }
}
