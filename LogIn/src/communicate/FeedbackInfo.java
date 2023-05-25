/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package communicate;

import java.io.Serializable;

/**
 *
 * @author abhi
 */
public class FeedbackInfo implements Serializable{
    private String comments,username;
    private int quality,custCare,ui,satisfy;
    private String datetime;
    
    public void setUserName(String user){
        username = user;
    }
    
    public void setDateTime(String datetime){
        this.datetime = datetime;
    }
    
    public String getDateTime(){
        return datetime;
    }
    
    public String getUserName(){
        return username;
    }
    
    public void set(int quality,int custCare,int ui,int satisfy){
        this.quality = quality;
        this.custCare = custCare;
        this.ui = ui;
        this.satisfy = satisfy;
    }
    
    public int getQuality(){
        return quality;
    }
    
    public int getCustCare(){
        return custCare;
    }
    
    public int getUi(){
        return ui;
    }
    
    public int getSatisfy(){
        return satisfy;
    }
    
    public void setComments(String msg){
        this.comments = msg;
    }
    
    public String getComments(){
        return comments;
    }
    
    public String[] toString1(){
        String[] res = new String[7];
        res[0] = username;
        res[1] = new String() + datetime;
        res[2] = new String() + custCare;
        res[3] = new String() + ui;
        res[4] = new String() + satisfy;
        res[5] = new String() + quality;
        res[6] = comments;
        return res;
    }
    
}
