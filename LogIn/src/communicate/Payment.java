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
public class Payment implements Serializable{
    private int total;
    public void setVal(int val){
        total = val;
    }
    public int getVal(){
        return total;
    }
}
