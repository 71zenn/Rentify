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
import userdata.OrderDao;
import java.util.List;

public class PurchaseHistoryController {

    private OrderDao orderDAO;

    public PurchaseHistoryController() {
        orderDAO = new OrderDao();
    }

    public List<Order> getPurchaseHistory(int userId) {
        return orderDAO.getPurchaseHistory(userId);
    }
}

