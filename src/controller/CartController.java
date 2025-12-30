/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dell
 */
import userdata.CartDAO;
import services.CheckoutService;
import model.CartItem;


        
import java.util.List;

public class CartController {

    private CartDAO cartDAO;
    private CheckoutService checkoutService;
    

    public CartController() {
        cartDAO = new CartDAO();
        checkoutService = new CheckoutService();
    }

    // Get cart items for logged-in user
    public List<CartItem> getCartItems(int userId) {
        return cartDAO.getCartItems(userId);
    }

    // Remove a single item from cart
    public void removeItemFromCart(int cartId) {
        cartDAO.removeFromCart(cartId);
    }

    // Checkout all cart items
    public void checkoutCart(List<CartItem> cartItems, int userId) {
        checkoutService.checkout(cartItems, userId);
    }
}

