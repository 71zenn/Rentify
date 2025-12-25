/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.MySQLConnection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.ProductModel;

public class ProductDao {
    MySQLConnection mysql = new MySQLConnection();
    public void createProduct(ProductModel product) {
        Connection conn = mysql.openConnection();
        String sql = "INSERT INTO products (productName , productImage, productPrice, productSynopsis, productType, productForm) VALUES ( ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductImage());
            pstmt.setInt(3, product.getProductPrice());
            pstmt.setString(4, product.getProductSynopsis());
            pstmt.setString(5, product.getProductImage());
            pstmt.setBoolean(6, product.getProductForm());
            
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            mysql.closeConnection(conn);
        }
    } 



     
    public List<ProductModel> getAllProducts() {
    List<ProductModel> products = new ArrayList<>();
    Connection conn = mysql.openConnection();
    String sql = "SELECT * FROM products";
/**
 *  private String productName; 
        private String productImage;
        private String productSynopsis;
        private Boolean productType;
        private Boolean productForm;
        private int productPrice; 
        * (int product_ID, String productName, String productImage , int productPrice, String productSynopsis, Boolean productType, Boolean productForm)
 */
    try (PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
            ProductModel product = new ProductModel(
                rs.getInt("id"),
                rs.getString("productName"),
                rs.getString("productImage"),
                rs.getInt("productPrice"),
                rs.getString("productSynopsis"),
                rs.getBoolean("productType"),
                rs.getBoolean("productType")   
            );
            products.add(product);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        mysql.closeConnection(conn);
    }
    return products;
}


public void updateProduct(ProductModel product) {
    Connection conn = mysql.openConnection();
    String sql = "UPDATE products SET productName=?, productImage=?, productPrice=? WHERE id=?";


    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      
    pstmt.setString(1, product.getProductName());
    pstmt.setString(2, product.getProductImage());
    pstmt.setInt(3, product.getProductPrice());
    pstmt.setInt(4, product.getProductID()); 
    pstmt.executeUpdate();


        pstmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        mysql.closeConnection(conn);
    }
}
}