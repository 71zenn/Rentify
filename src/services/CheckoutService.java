/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Dell
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;
import model.CartItem;
import database.DBConnection;


public class CheckoutService {

    public void checkout(List<CartItem> cartItems, int userId) {

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            for (CartItem item : cartItems) {

                if ("BUY".equals(item.getActionType())) {
                    insertOrder(con, item, userId);
                } else {
                    insertRental(con, item, userId);
                }

                removeFromCart(con, item.getCartId());
            }

            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertOrder(Connection con, CartItem item, int userId) throws Exception {
        String sql = """
            INSERT INTO orders(user_id, item_id, item_type, price)
            VALUES (?,?,?,?)
        """;

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, item.getItemId());
            ps.setString(3, item.getItemType());
            ps.setDouble(4, item.getPrice());
            ps.executeUpdate();
        }
    }

    private void insertRental(Connection con, CartItem item, int userId) throws Exception {
    String sql = """
        INSERT INTO rentals(user_id, item_id, status)
        VALUES (?,?, 'ACTIVE')
    """;

    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, userId);
        ps.setInt(2, item.getItemId());
        ps.executeUpdate();
    }
}

    private void removeFromCart(Connection con, int cartId) throws Exception {
    String sql = "DELETE FROM cart_items WHERE cart_id=?";
    try (PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setInt(1, cartId);
        ps.executeUpdate();
    }
}

}

