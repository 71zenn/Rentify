/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import database.MySQLConnection;
import model.PurchaseHistoryModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Dell
 */
public class PurchaseHistoryDao {
    
    public List<PurchaseHistoryModel> getPurchaseHistory(int userId) {
        List<PurchaseHistoryModel> list = new ArrayList<>();

        String sql = """
            SELECT o.order_id, o.purchase_date, o.item_type, o.price,
                   p.productName, p.productForm, p.productImage
            FROM orders o
            JOIN products p ON p.id = o.item_id
            WHERE o.user_id = ?
            ORDER BY o.purchase_date DESC
        """;

        try (Connection conn = new MySQLConnection().openConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new PurchaseHistoryModel(
                            rs.getInt("order_id"),
                            rs.getTimestamp("purchase_date"),
                            rs.getString("item_type"),
                            rs.getDouble("price"),
                            rs.getString("productName"),
                            rs.getString("productForm"),
                            rs.getString("productImage")
                    ));
                }
            }

        } catch (Exception e) {
            System.out.println("getPurchaseHistory error: " + e.getMessage());
        }

        return list;
    }
}

