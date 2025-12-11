/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;
import Model.User_model;
import database.MySQLConnection;
import java.sql.*;

public class userDao {
    MySQLConnection mysql = new MySQLConnection();
    
    public void signup(User_model user){
        Connection conn = mysql.openConnection();
        String sql = "insert into user (username, email, password) values(?,?,?)";
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
        String sql = "select * from user where email = ? or username = ?";
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

    public void signUp(User_model usermodel) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
