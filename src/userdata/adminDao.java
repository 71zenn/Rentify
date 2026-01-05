/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import model.User_model;
import database.MySQLConnection;
import java.util.List;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author sthaa
 */
public class adminDao {
    MySQLConnection mysql = new MySQLConnection();
        public void users(User_model user){
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
    public void updateUser(User_model user) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE user SET username = ?, email = ?, password = ? WHERE UserID = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, user.getUsername());
            pstm.setString(2, user.getEmail());
            pstm.setString(3, user.getPassword());
            pstm.setInt(4, user.getUserID());
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }

    public void deleteUser(int userId) {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM user WHERE UserID = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, userId);
            pstm.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
    }

    public User_model getUserById(int userId) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM user WHERE UserID = ?";
        User_model user = null;
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setInt(1, userId);
            ResultSet result = pstm.executeQuery();
            if (result.next()) {
                user = new User_model(result.getString("username"), result.getString("email"), result.getString("password"));
                user.setUserID(result.getInt("UserID"));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return user;
    }

    public List<User_model> getAllUsers() {
        List<User_model> users = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM user";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            ResultSet result = pstm.executeQuery();
            while (result.next()) {
                User_model user = new User_model(result.getString("username"), result.getString("email"), result.getString("password"));
                user.setUserID(result.getInt("UserID"));
                users.add(user);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return users;
    }
    public boolean login(String username, String password) {
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM admin WHERE username = ? AND password = ?";
        try (PreparedStatement pstm = conn.prepareStatement(sql)) {
            pstm.setString(1, username);
            pstm.setString(2, password);
            ResultSet rs = pstm.executeQuery();
            return rs.next(); // true if a row exists
        } catch (Exception ex) {
            System.out.println(ex);
        } finally {
            mysql.closeConnection(conn);
        }
        return false;
    }

}

