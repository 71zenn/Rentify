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
String sql = "INSERT INTO products (productName , productImage, productPrice, " +
             "productSynopsis, productType, productForm, productQuantity) " +
             "VALUES ( ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductImage());
            pstmt.setInt(3, product.getProductPrice());
            pstmt.setString(4, product.getProductSynopsis());
            pstmt.setString(5, product.getProductType());
            pstmt.setString(6, product.getProductForm());
            pstmt.setInt(7, product.getProductQuantity());
            
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

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ProductModel product = new ProductModel(
                    rs.getInt("id"),
                    rs.getString("productName"),
                    rs.getString("productImage"),
                    rs.getInt("productPrice"),
                    rs.getString("productSynopsis"),
                    rs.getString("productType"),
                    rs.getString("productForm"),  
                    rs.getInt("productQuantity")
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

    public List<ProductModel> getAllFavouriteProducts() {
            List<ProductModel> products = new ArrayList<>();
            Connection conn = mysql.openConnection();
            String sql = "SELECT * FROM products";

            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ProductModel product = new ProductModel(
                        rs.getInt("id"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getInt("productPrice"),
                        rs.getString("productSynopsis"),
                        rs.getString("productType"),
                        rs.getString("productForm"),  
                        rs.getInt("productQuantity")   
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
        String sql = "UPDATE products SET " +
                     "productName=?, productImage=?, productPrice=?, " +
                     "productSynopsis=?, productType=?, productForm=?, " +
                     "productQuantity=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, product.getProductName());
            pstmt.setString(2, product.getProductImage());
            pstmt.setInt(3, product.getProductPrice());
            pstmt.setString(4, product.getProductSynopsis());
            pstmt.setString(5, product.getProductType());
            pstmt.setString(6, product.getProductForm());
            pstmt.setInt(7, product.getProductQuantity());
            pstmt.setInt(8, product.getProductID());

            int rows = pstmt.executeUpdate();   // only once
            System.out.println("UPDATED rows = " + rows);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
    }


    public ProductModel getProductById(int id) {
        ProductModel product = null;
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM products WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    product = new ProductModel(
                        rs.getInt("id"),
                        rs.getString("productName"),
                        rs.getString("productImage"),
                        rs.getInt("productPrice"),
                        rs.getString("productSynopsis"),
                        rs.getString("productType"),
                        rs.getString("productForm"),
                        rs.getInt("productQuantity")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return product;
    }
    public void deleteProduct(int id) {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM products WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
    }
}