/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import userdata.userDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.User_model;
import view.registration;

public class userController{
    private final userDao userdao = new userDao();
    private final registration userView;
    
    public userController(registration userView){
         this.userView = userView;
         this.userView.addUserListener(new SignUpListener());
        
    }
    public void open(){
            this.userView.setVisible(true);
    }
    public void close(){
            this.userView.dispose();
    }
    
    
    class SignUpListener implements ActionListener {
@Override
            public void actionPerformed(ActionEvent e){
                try{
                    String fullname = userView.getFullNameField().getText();
                    String username = userView.getUsernameField().getText();
                    String email    = userView.getEmailField().getText();
                    char[] passwordChars = userView.getPasswordField().getPassword();
                    String password = new String(passwordChars);
                    
                    User_model usermodel = new User_model( username, email, password);
                    boolean check = userdao.check(usermodel);
                    if(check){
                        JOptionPane.showMessageDialog(userView, "Already exist");
                    }else{
                        userdao.signup(usermodel,fullname);
                        JOptionPane.showMessageDialog(userView, "Successfull");
                    }
                }catch(Exception ext){
                    System.out.println(ext);
                }   
            }
    }
}