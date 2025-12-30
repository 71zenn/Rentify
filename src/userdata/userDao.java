/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;
import database.MySQLConnection;
import model.User_model;

import java.sql.*;

public class userDao {
    MySQLConnection mysql = new MySQLConnection();
    
    public void signup(User_model user){
        Connection conn = mysql.openConnection();
        String sql = "insert into users (username, email, password) values(?,?,?)";
        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.executeUpdate();
            
        } catch(Exception ex){
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }
    public boolean check(User_model user){
        Connection conn = mysql.openConnection();
        String sql = "select * from users where email = ? or username = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)){
            pstm.setString(1, user.getEmail());
            pstm.setString(2, user.getUsername());
            ResultSet result = pstm.executeQuery();
            return result.next();
        } catch(Exception ex){
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }
    
    public boolean login(User_model user){
    Connection conn = mysql.openConnection();
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

    try(PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setString(1, user.getUsername());
        pstm.setString(2, user.getPassword());

        ResultSet rs = pstm.executeQuery();

        return rs.next();  // true if login success

    } catch (Exception ex) {
        System.out.println("Login error: " + ex);
        return false;
    } finally {
        mysql.closeConnection(conn);
    }
}
    
 public User_model loginAndGetUser(User_model user) {
    Connection conn = mysql.openConnection();
    if (conn == null) {
        System.out.println("Connection is NULL!");
        return null;
    }

    String sql = "SELECT user_id, username, email FROM users WHERE username = ? AND password = ?";

    try (PreparedStatement pstm = conn.prepareStatement(sql)) {

        pstm.setString(1, user.getUsername());
        pstm.setString(2, user.getPassword());

        try (ResultSet rs = pstm.executeQuery()) {
            if (rs.next()) {
                User_model u = new User_model();
                u.setUserID(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                return u;
            }
        }

    } catch (Exception ex) {
        System.out.println("Login error: " + ex);
    } finally {
        mysql.closeConnection(conn);
    }

    return null;
}


}
