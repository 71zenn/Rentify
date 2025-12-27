/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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
    public void setUsername(String username){
        this.username = username; 
}
    public String getUsername(){
        return username;
}
    public void setEmail(String email){
        this.email = email; 
}
    public String getEmail(){
        return email;
    }
    public void setPasssword(String password){
        this.password = password; 
}
    public String getPassword(){
        return password;
    }
    
    public void setUserID(int user_id){
        this.user_id = user_id; 
}
    public int getUserID(){
        return user_id;
}
}

