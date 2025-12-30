/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

/**
 *
 * @author Dell
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.CartItem;
import database.DBConnection;



public class CartDAO {

    public List<CartItem> getCartItems(int userId) {

        List<CartItem> list = new ArrayList<>();

        String sql = """
    SELECT cart_id, item_id, item_name, item_type, action_type, price, image, quantity
    FROM cart_items
    WHERE user_id = ?
""";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
    CartItem item = new CartItem();
    item.setCartId(rs.getInt("cart_id"));
    item.setItemId(rs.getInt("item_id"));
    item.setItemName(rs.getString("item_name"));
    item.setItemType(rs.getString("item_type"));
    item.setActionType(rs.getString("action_type"));
    item.setPrice(rs.getDouble("price"));
    item.setImage(rs.getString("image"));
    item.setQuantity(rs.getInt("quantity"));
    list.add(item);
}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void removeFromCart(int cartId) {

        String sql = "DELETE FROM cart_items WHERE cart_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, cartId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
