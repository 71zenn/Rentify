package model;

public class User_model {
    private String username, password, email;
    private int user_id;

    public User_model() { }  // ✅ add this (helps DAO/controller)

    public User_model(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
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
