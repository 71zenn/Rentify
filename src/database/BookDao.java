/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

import Model.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class BookDao {
     
    public void insert(Book b) {
           String sql = "INSERT INTO books(name, type, image_path) VALUES(?, ?, ?)";
                    try (Connection con = new MySQLConnection().openConnection();
               PreparedStatement ps = con.prepareStatement(sql)){
              ps.setString(1, b.name);
              ps.setString(2, b.type);
              ps.setString(3, b.imagePath);
              ps.executeUpdate();   
              
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        public List<Book> getAll(){
            List<Book> list = new ArrayList<>();
            String sql = "SELECT id, name, type, image_path FROM books";
    
            try(Connection con = new MySQLConnection().openConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
        
             while (rs.next()){
                Book b = new Book();
                b.id = rs.getInt("id");
                b.name = rs.getString("name");
                b.type = rs.getString("type");
                b.imagePath = rs.getString("image_path");
                list.add(b);
            }
        } catch (SQLException e){
             e.printStackTrace();
             
        }
        return list;
    }
}
    

