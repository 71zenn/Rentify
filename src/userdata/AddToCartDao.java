/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import database.MySQLConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CartModel;

public class AddToCartDao {

    private final MySQLConnection db = new MySQLConnection();

    public List<CartModel> getCartItems(int userId, String search) {
        List<CartModel> list = new ArrayList<>();

        String sql = "SELECT c.cart_id, c.user_id, c.product_id, c.quantity, c.action_type, " +
                "       p.productName, p.productImage, p.productPrice, p.productType, p.productForm " +
                "FROM cart c " +
                "JOIN product p ON p.product_ID = c.product_id " +
                "WHERE c.user_id = ? " +
                "  AND ( ? IS NULL OR p.productName LIKE ? ) " +
                "ORDER BY c.cart_id DESC";

        try (Connection conn = db.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);

            if (search == null || search.trim().isEmpty()) {
                ps.setNull(2, Types.VARCHAR);
                ps.setNull(3, Types.VARCHAR);
            } else {
                ps.setString(2, search.trim());
                ps.setString(3, "%" + search.trim() + "%");
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CartModel item = new CartModel(
                            rs.getInt("cart_id"),
                            rs.getInt("user_id"),
                            rs.getInt("product_id"),
                            rs.getInt("quantity"),
                            rs.getString("action_type"),
                            rs.getString("productName"),
                            rs.getString("productImage"),
                            rs.getInt("productPrice"),
                            rs.getString("productType"),
                            rs.getString("productForm"));
                    list.add(item);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateQuantity(int cartId, int newQty) {
        String sql = "UPDATE cart SET quantity = ? WHERE cart_id = ?";
        try (Connection conn = db.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, newQty);
            ps.setInt(2, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteCartItem(int cartId) {
        String sql = "DELETE FROM cart WHERE cart_id = ?";
        try (Connection conn = db.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addToCart(int userId, int productId, int quantity, String actionType) {
        String sql = "INSERT INTO cart (user_id, product_id, quantity, action_type) VALUES (?, ?, ?, ?)";
        try (Connection conn = db.openConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ps.setInt(3, quantity);
            ps.setString(4, actionType); // e.g., "buy" or "rent"

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
