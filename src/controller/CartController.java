/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dell
 */
import userdata.CartDao;
import services.CheckoutService;
import model.CartItem;


        
import java.util.List;

public class CartController {

    private CartDao cartDAO;
    private CheckoutService checkoutService;
    

    public CartController() {
        cartDAO = new CartDao();
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
    
    public void updateCartQuantity(int cartId, int newQty) {
        cartDAO.updateQuantity(cartId, newQty);
    }

    // ✅ NEW: delete all cart items (top DELETE button)
    public void clearCart(int userId) {
        cartDAO.clearCart(userId);
    }
    
    public void removeSelectedItems(java.util.List<Integer> cartIds) {
    cartDAO.removeSelected(cartIds);
}


    // Checkout all cart items
    public void checkoutCart(List<CartItem> cartItems, int userId) {
        checkoutService.checkout(cartItems, userId);
    }
    
    
    
    
}

