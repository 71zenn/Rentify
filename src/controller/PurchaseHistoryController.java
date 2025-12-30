/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dell
 */
import model.Order;
import userdata.OrderDAO;
import java.util.List;

public class PurchaseHistoryController {

    private OrderDAO orderDAO;

    public PurchaseHistoryController() {
        orderDAO = new OrderDAO();
    }

    public List<Order> getPurchaseHistory(int userId) {
        return orderDAO.getPurchaseHistory(userId);
    }
}

