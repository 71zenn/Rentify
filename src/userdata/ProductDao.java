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
}