/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import java.sql.*;
import java.util.ArrayList;
import model.Product;
import database.MySQLConnection;

/**
 *
 * @author Dell
 */
public class ProductDAO {
    
 public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();

        String sql = "SELECT id, productName, productImage, productPrice, productType, productForm " +
                     "FROM products WHERE productQuantity > 0";

     MySQLConnection db = new MySQLConnection();

    try (Connection conn = db.openConnection();
     PreparedStatement pst = conn.prepareStatement(sql);
     ResultSet rs = pst.executeQuery()) {


            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setProductName(rs.getString("productName"));
                p.setProductImage(rs.getString("productImage"));
                p.setProductPrice(rs.getInt("productPrice"));
                p.setProductType(rs.getString("productType"));
                p.setProductForm(rs.getString("productForm"));
                products.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return products;
    }
}
