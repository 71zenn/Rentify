package services;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Dell
 */
import java.util.List;
import userdata.CartDAO;
import model.CartItem;
public class CartService {

    private CartDAO cartDAO;

    public CartService() {
        cartDAO = new CartDAO();
    }

    public List<CartItem> getCartItems(int userId) {
        return cartDAO.getCartItems(userId);
    }

    public void removeItem(int cartId) {
        cartDAO.removeFromCart(cartId);
    }
}

