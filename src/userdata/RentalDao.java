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
import model.Rental;
import database.DBConnection;
public class RentalDao {

    public List<Rental> getRentalHistory(int userId) {

        List<Rental> list = new ArrayList<>();

        String sql = """
            SELECT i.name, r.rental_date, r.return_date, r.status
            FROM rentals r
            JOIN items i ON r.item_id = i.item_id
            WHERE r.user_id = ?
            ORDER BY r.rental_date DESC
        """;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental();
                rental.setItemName(rs.getString("name"));
                rental.setRentalDate(rs.getTimestamp("rental_date"));
                rental.setReturnDate(rs.getDate("return_date"));
                rental.setStatus(rs.getString("status"));
                list.add(rental);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

