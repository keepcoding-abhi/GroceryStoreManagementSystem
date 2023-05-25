/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicate;

import login.LogInCredentials;
import java.io.Serializable;
/**
 *
 * @author abhi
 */
public class GetUserInfo implements Serializable{
    private String msg;
    private LogInCredentials client;
    public GetUserInfo(LogInCredentials lic){
        client = lic;
        msg = "Please send User Profile";
    }
    
    public LogInCredentials getLogInCredentials(){
        return client;
    }
}
