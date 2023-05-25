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
public class ErrorMessage implements Serializable{
    private String msg;
    public void setMsg(String report){
        msg = report;
    }
    public String getMsg(){
        return msg;
    }
}
