/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerSide;

import java.sql.Connection;
import login.UserProfile;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import login.LogInCredentials;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.awt.Image;
import java.sql.ResultSet;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import login.UserProfile;
import java.util.ArrayList;
import communicate.ErrorMessage;
import javax.swing.ImageIcon;
import communicate.FeedbackInfo;
import communicate.Success;
import javax.swing.JFrame;
import communicate.Payment;
import communicate.FeedbackInfo;
import communicate.Order;

/**
 *
 * @author abhi
 */
public class DBAccess1 extends javax.swing.JFrame {

    /**
     * Creates new form DBAccess1
     */
    Connection con;
    private JFrame prev;
    private boolean connected;
    
    public synchronized Object getFeedback(){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Feedback");
            ResultSet rs = ps.executeQuery();
            ArrayList<FeedbackInfo> feedback = new ArrayList<FeedbackInfo>();
            FeedbackInfo temp;
            int qual,custcare,ui,satisfy;
            while(rs.next()){
                temp = new FeedbackInfo();
                temp.setUserName(rs.getString("Username"));
                temp.setDateTime(rs.getString("DateAndTime"));
                qual = rs.getInt("QualityOfProducts");
                custcare = rs.getInt("CustomerCare");
                ui = rs.getInt("UserInterface");
                satisfy = rs.getInt("CustomerSatisfaction");
                temp.setComments(rs.getString("Comments"));
                temp.set(qual,custcare,ui,satisfy);
                feedback.add(temp);
            }
            return feedback;
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ErrorMessage emsg = new ErrorMessage();
        emsg.setMsg("Error reading feedback");
        return emsg;
    }
    
    public synchronized Object purchase(int id){
        int temp = id/100;
        String tables;
        switch(temp){
            case 3311:
                    tables = "Cricket";
                    break;
                case 3322:
                    tables = "Tennis";
                    break;
                case 3333:
                    tables = "Football";
                    break;
                case 3344:
                    tables = "Badminton";
                    break;
                case 1111:
                    tables = "Fruits";
                    break;
                case 1122:
                    tables = "Vegetables";
                    break;
                case 1133:
                    tables = "Biscuits";
                    break;
                case 1144:
                    tables = "Soft_drinks";
                    break;
                case 2211:
                    tables = "Face";
                    break;
                case 2222:
                    tables = "Hair";
                    break;
                case 2233:
                    tables = "Body_soap";
                    break;
                default:
                    ErrorMessage emsg = new ErrorMessage();
                    emsg.setMsg("Invalid ID");
                    return emsg;
        }
        String command = "SELECT * FROM "+ tables + " WHERE ID = "+ id;
        try{
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(command);
            if(rs.next()){
                Order toSend = new Order();
                toSend.setID(rs.getInt("ID"));
                toSend.setName(rs.getString("Product"));
                toSend.setQuantity(rs.getInt("Quantity"));
                toSend.setCost(rs.getInt("Cost"));
                return toSend;
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ErrorMessage emsg = new ErrorMessage();
        emsg.setMsg("Error");
        return emsg;
    }
    
    public synchronized int updateBalance(int new1){
        try{
            PreparedStatement ps = con.prepareStatement("UPDATE Balance SET Bal = ?");
            ps.setInt(1,new1);
            int status = ps.executeUpdate();
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
            return -1;
        }
        return balance();
    }
    
    public synchronized int balance(){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM Balance");
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int bal = rs.getInt("Bal");
                return bal;
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        return -1;
    }
    
    public synchronized Object filter(int limit){
        String table;
        try{
            String command = "SELECT * FROM TableNames";
            Statement stmt = con.createStatement();
            Statement stmt1 = con.createStatement();
            ResultSet rs = stmt.executeQuery(command);
            ResultSet rs1;
            Order temp;
            ArrayList<Order> Filtered = new ArrayList();
            while(rs.next()){
                table = rs.getString("Name");
                command = "SELECT * FROM " + table +" WHERE Quantity < " + limit;
                rs1 = stmt1.executeQuery(command);
                while(rs1.next()){
                    temp = new Order();
                    temp.setName(rs1.getString("Product"));
                    temp.setID(rs1.getInt("ID"));
                    temp.setQuantity(rs1.getInt("Quantity"));
                    temp.setCost(rs1.getInt("Cost"));
                    Filtered.add(temp);
                }
            }
            return Filtered;
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
            System.out.println(exc);
        }
        return new ErrorMessage();
    }
    
    public synchronized Object pay(Payment payment){
        int current;
        try{
            int added = payment.getVal();
            Statement stmt = con.createStatement();
            String command = "SELECT * FROM Balance";
            ResultSet rs = stmt.executeQuery(command);
            if(rs.next()){
                current = rs.getInt("Bal");
                current += added;
                command = "UPDATE Balance SET Bal = " + current;
                stmt.executeUpdate(command);
                Success succ = new Success();
                succ.setMsg("Transaction Successful");
                return succ;
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ErrorMessage emsg = new ErrorMessage();
        emsg.setMsg("Error in reading balance");
        return emsg;
    }
    
    public synchronized Object scanOrder(Order request){
        int id = request.getID();
        try{
            String command,prodID;
            Statement stmt = con.createStatement();
            String tables = new String();
            int temp = id/100;
            switch(temp){
                case 3311:
                    tables = "Cricket";
                    break;
                case 3322:
                    tables = "Tennis";
                    break;
                case 3333:
                    tables = "Football";
                    break;
                case 3344:
                    tables = "Badminton";
                    break;
                case 1111:
                    tables = "Fruits";
                    break;
                case 1122:
                    tables = "Vegetables";
                    break;
                case 1133:
                    tables = "Biscuits";
                    break;
                case 1144:
                    tables = "Soft_drinks";
                    break;
                case 2211:
                    tables = "Face";
                    break;
                case 2222:
                    tables = "Hair";
                    break;
                case 2233:
                    tables = "Body_soap";
                    break;
                default:
                    ErrorMessage emsg = new ErrorMessage();
                    emsg.setMsg("Invalid ID");
                    return emsg;
            }
            command = new String();
            command += "SELECT * FROM " + tables + " WHERE ID = " + id;
            ResultSet rs = stmt.executeQuery(command);
            if(rs.next()){
                if(request.getAdd()){
                    int available = rs.getInt("Quantity");
                    int requested = request.getQuant();
                    if(requested > available){
                        ErrorMessage emsg = new ErrorMessage();
                        emsg.setMsg("Sorry we only have "+ available + " " + request.getName() + " left");
                        return emsg;
                    }
                    available -= requested;
                    PreparedStatement ps1 = con.prepareStatement("UPDATE " + tables +" SET Quantity = ? WHERE ID = ?");
                    ps1.setInt(1,available);
                    ps1.setInt(2,id);
                    int status = ps1.executeUpdate();
                    System.out.println(status + " rows were updated");
                    Success succ = new Success();
                    succ.setMsg("You can add item to cart");
                    return succ;
                }
                else{
                    int available = rs.getInt("Quantity");
                    available += request.getQuant();
                    command = "UPDATE " + tables + " SET Quantity = " + available + " WHERE ID = " + id;
                    stmt.executeUpdate(command);
                    Success succ = new Success();
                    succ.setMsg("Updated Successfully");
                    return succ;
                }
            }
            else{
                ErrorMessage emsg = new ErrorMessage();
                emsg.setMsg("Invalid ID");
                return emsg;
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ErrorMessage emsg = new ErrorMessage();
        emsg.setMsg("Error");
        return emsg;
    }
    
    public synchronized Object addFeedback(FeedbackInfo feed){
        try{
            PreparedStatement stmt = con.prepareStatement("INSERT INTO Feedback(Username,QualityOfProducts,CustomerCare,UserInterface,CustomerSatisfaction,Comments) values (?,?,?,?,?,?)"); 
            stmt.setString(1,feed.getUserName());
            stmt.setInt(2, feed.getQuality());
            stmt.setInt(3,feed.getCustCare());
            stmt.setInt(4, feed.getUi());
            stmt.setInt(5, feed.getSatisfy());
            stmt.setString(6,feed.getComments());
            int status = stmt.executeUpdate();
            System.out.println(status + " rows affected");
            Success succ= new Success();
            succ.setMsg("Feedback added successfully");
            stmt.close();
            return succ;
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        ErrorMessage emsg = new ErrorMessage();
        emsg.setMsg("Error");
        return emsg;
    }
    
    public synchronized Object getUserProfile(LogInCredentials client){
        UserProfile toSend = new UserProfile();
        Statement stmt = null;
        try{
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM LogInCredentials WHERE username = '" + client.getUserName() + "' and password = '"+ client.getPassword() + "'");
            if(rs.next()){
                rs = stmt.executeQuery("SELECT * FROM UserInfo WHERE username = '" + client.getUserName() +"'");
                if(rs.next()){
                    toSend.setName(rs.getString("FirstName"), rs.getString("MiddleName"), rs.getString("LastName"));
                    toSend.setContact(rs.getString("Country"), rs.getString("Mobile"), rs.getString("Email"));
                    toSend.setLogin(client.getUserName(), client.getPassword());
                    String signPath,profPath;
                    signPath = rs.getString("Signature");
                    profPath = rs.getString("ProfilePhoto");
                    ImageIcon sign = new ImageIcon(signPath);
                    ImageIcon prof = new ImageIcon(profPath);
                    toSend.setPhotos(prof, sign);
                    return toSend;
                }
                else{
                    ErrorMessage emsg = new ErrorMessage();
                    emsg.setMsg("User Info for " + client.getUserName() + " does not exist.");
                    return emsg;
                }
            }
            else{
                ErrorMessage emsg = new ErrorMessage();
                emsg.setMsg("No such account exists");
                return emsg;
            }
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        finally{
            try{
                stmt.close();
            }catch(SQLException exc){
                JOptionPane.showMessageDialog(null,exc,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        return toSend;
    }
    
    public synchronized void checkUsrPass(LogInCredentials lic){
        PreparedStatement ps = null;
        try{
            String user = lic.getUserName();
            String pass = lic.getPassword();
            ps = con.prepareStatement("SELECT * FROM LogInCredentials");
            ResultSet rs = ps.executeQuery();
            String readUser,readPass;
            while(rs.next()){
                readUser = rs.getString("username");
                readPass = rs.getString("password");
                if(readUser.equals(user)){
                    lic.foundUser(true);
                    if(readPass.equals(pass)){
                        lic.foundPass(true);
                    }
                    else{
                        lic.foundPass(false);
                    }
                    return;
                }
            }
            lic.foundUser(false);
            lic.foundPass(false);
        }
        catch(SQLException exc){
            JOptionPane.showMessageDialog(this,exc,"Error",JOptionPane.ERROR_MESSAGE);
        }
        finally{
            try{
                ps.close();
            }
            catch(SQLException exc){
                JOptionPane.showMessageDialog(this,exc,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public synchronized void addAccount(UserProfile up){
        PreparedStatement ps = null;
        try{
               String user = up.getUserName();
               String pass = up.getPass();
               ps = con.prepareStatement("INSERT INTO LogInCredentials (username,password) values (?,?)");
               ps.setString(1,user);
               ps.setString(2,pass);
               int status = ps.executeUpdate();
               System.out.println(status + " rows affected");
               Image img = up.getSign().getImage();
               BufferedImage bim = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
               Graphics2D g2 = bim.createGraphics();
               g2.drawImage(img,0,0,null);
               g2.dispose();
               String path = "/home/abhi/NetBeansProjects/Server/";
               String sign = path + user +"_sign.jpeg";
               ImageIO.write(bim,"jpeg",new File(sign));
               img = up.getProfile().getImage();
               bim = new BufferedImage(img.getWidth(null),img.getHeight(null),BufferedImage.TYPE_INT_RGB);
               String prof = path + user + "_pic.jpeg";
               g2 = bim.createGraphics();
               g2.drawImage(img,0,0,null);
               g2.dispose();
               ImageIO.write(bim,"jpeg",new File(prof));
               String country,email,mobile;
               country = up.getCountry();
               email = up.getEmail();
               mobile = up.getMobile();
               ps = con.prepareStatement("INSERT INTO UserInfo (username,FirstName,MiddleName,LastName,Country,Email,Mobile,ProfilePhoto,Signature) values(?,?,?,?,?,?,?,?,?)");
               ps.setString(1,user);
               ps.setString(2,up.getFirstName());
               ps.setString(3,up.getMiddleName());
               ps.setString(4,up.getLastName());
               ps.setString(5,up.getCountry());
               ps.setString(6,up.getEmail());
               ps.setString(7,up.getMobile());
               ps.setString(8,prof);
               ps.setString(9,sign);
               status = ps.executeUpdate();
               System.out.println(status + " rows affected.");
        }
        catch(SQLException | IOException sqex){
            JOptionPane.showMessageDialog(this,sqex,"Error",JOptionPane.ERROR);
        }
        finally{
            try{
                ps.close();
            }
            catch(SQLException exc){
                JOptionPane.showMessageDialog(this,exc,"Error",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public DBAccess1(JFrame f) {
        prev = f;
        connected = false;
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPasswordField1 = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel2.setText("Username:");

        jTextField1.setText("Username");
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Password:");

        jPasswordField1.setText("Password");
        jPasswordField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPasswordField1MouseClicked(evt);
            }
        });
        jPasswordField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Submit");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jTextField1))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jButton1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jPasswordField1, jTextField1});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 25)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(9, 17, 235));
        jLabel1.setText("Database Login");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jPasswordField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jPasswordField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPasswordField1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        String temp = jTextField1.getText();
        if(temp.equals("Username")){
            jTextField1.setText("");
        }
    }//GEN-LAST:event_jTextField1MouseClicked

    private void jPasswordField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPasswordField1MouseClicked
        String temp = new String(jPasswordField1.getPassword());
        if(temp.equals("Password")){
            jPasswordField1.setText("");
        }
    }//GEN-LAST:event_jPasswordField1MouseClicked

    private void jButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseClicked
        try{
            Class.forName("com.mysql.jdbc.Driver");
            String user,pass;
            user = jTextField1.getText();
            pass = new String(jPasswordField1.getPassword());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql48",user,pass);
            JOptionPane.showMessageDialog(this,"Database login successful");
            setVisible(false);
            prev.setVisible(true);
            connected = true;
        }
        catch(ClassNotFoundException |SQLException exc){
            JOptionPane.showMessageDialog(this,"Invalid username or password","Error",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DBAccess1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DBAccess1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DBAccess1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DBAccess1.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        DBAccess1 dba;
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new DBAccess1().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField jPasswordField1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
