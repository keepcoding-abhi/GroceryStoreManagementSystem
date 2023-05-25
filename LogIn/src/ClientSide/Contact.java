/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientSide;

import java.net.Socket;
import java.net.UnknownHostException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author abhi
 */
public class Contact {
    public static Object transmit(Object obj) throws UnknownHostException,IOException,ClassNotFoundException{
        Socket client = new Socket("localhost",3173);
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        out.writeObject(obj);
        ObjectInputStream oin = new ObjectInputStream(client.getInputStream());
        obj = oin.readObject();
        return obj;
    }
}
