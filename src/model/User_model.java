/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zenni
 */
public class User_model {
    private String username, password, email;
    private int user_id;
    
    public User_model(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User_model() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String getUsername() { return username; }
    public void setUsername(String username){ this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email){ this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password){ this.password = password; } // ✅ fix name

    public int getUserID(){ return user_id; }
    public void setUserID(int user_id){ this.user_id = user_id; }
}
