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
public class Success implements Serializable{
    private String msg;
    public void setMsg(String msg){
        this.msg = msg;
    }
    public String getMsg(){
        return msg;
    }
}
