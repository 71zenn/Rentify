package userdata;

import database.MySQLConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrderDAO {

    private final MySQLConnection mysql = new MySQLConnection();

    public List<Order> getPurchaseHistory(int userId) {

        String sql = """
            SELECT i.name, o.price, o.item_type, o.purchase_date
            FROM orders o
            JOIN items i ON o.item_id = i.item_id
            WHERE o.user_id = ?
            ORDER BY o.purchase_date DESC
        """;

        Connection con = mysql.openConnection();
        if (con == null) {
            System.out.println("OrderDAO: DB connection is null!");
            return Collections.emptyList();
        }

        List<Order> list = new ArrayList<>();

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    Order order = new Order();
                    order.setItemName(rs.getString("name"));
                    order.setPrice(rs.getDouble("price"));
                    order.setItemType(rs.getString("item_type"));
                    order.setPurchaseDate(rs.getTimestamp("purchase_date"));
                    list.add(order);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mysql.closeConnection(con);
        }

        return list;
    }
}
