/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Model.Product;
import database.MySQLConnection;
import java.sql.*;
import java.util.*;


public class ProductDao {
     
    public void insert(Product p) {
           String sql = "INSERT INTO Products(name, type, image_path, category) VALUES(?, ?, ?, ?)";
                    try (Connection con = new MySQLConnection().openConnection();
               PreparedStatement ps = con.prepareStatement(sql)){
              ps.setString(1, p.name);
              ps.setString(2, p.type);
              ps.setString(3, p.imagepath);
              ps.setString(4, p.category);
              ps.executeUpdate();   
              
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
        public List<Product> getAll(){
            List<Product> list = new ArrayList<>();
            String sql = "SELECT id, name, type, image_path, category FROM products";
    
            try(Connection con = new MySQLConnection().openConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()){
        
             while (rs.next()){
                Product p = new Product();
                p.id = rs.getInt("id");
                p.name = rs.getString("name");
                p.type = rs.getString("type");
                p.imagepath = rs.getString("image_path");
                p.category = rs.getString("category");
                list.add(p);
            }
        } catch (SQLException e){
             e.printStackTrace();
             
        }
        return list;
    }
}
    

