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
public class Order implements Serializable{
    private String Name;
    private int productID,quantity,cost;
    private boolean add;
    public void setName(String str){
        Order.this.Name = str;
    }
    public void setID(int id){
        productID = id;
    }
    public void setQuantity(int quant){
        quantity = quant;
    }
    public void setCost(int cost){
        this.cost = cost;
    }
    public String getName(){
        return Name;
    }
    public int getID(){
        return productID;
    }
    public int getQuant(){
        return quantity;
    }
    public int getCost(){
        return cost;
    }
    public void setAdd(boolean add){
        this.add = add;
    }
    public boolean getAdd(){
        return add;
    }
    
    public String[] toString1(){
        String[] res = new String[4];
        res[0] = Name;
        res[1] = new String();
        res[1] += productID;
        res[2] = new String();
        res[2] += quantity;
        res[3] = new String();
        res[3] += cost;
        return res;
    }
    
    public Order clone(){
        Order ret = new Order();
        ret.Name = new String(this.Name);
        ret.productID = this.productID;
        ret.quantity = this.quantity;
        ret.cost = this.cost;
        ret.add = this.add;
        return ret;
    }
}
