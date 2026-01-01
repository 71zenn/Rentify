/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Dell
 */
import userdata.OrderDao;
import java.util.List;
import model.Order;


public class OrderService {

    private OrderDao orderDAO;

    public OrderService() {
        orderDAO = new OrderDao();
    }

    public List<Order> getPurchaseHistory(int userId) {
        return orderDAO.getPurchaseHistory(userId);
    }
}
