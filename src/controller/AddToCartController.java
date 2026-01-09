/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.List;
import model.CartModel;
import userdata.AddToCartDao;

public class AddToCartController {

    private final AddToCartDao dao = new AddToCartDao();

    public List<CartModel> getCartItems(int userId, String search) {
        return dao.getCartItems(userId, search);
    }

    public boolean increaseQty(int cartId, int currentQty) {
        return dao.updateQuantity(cartId, currentQty + 1);
    }

    public boolean decreaseQty(int cartId, int currentQty) {
        int next = Math.max(1, currentQty - 1);
        return dao.updateQuantity(cartId, next);
    }

    public boolean deleteItem(int cartId) {
        return dao.deleteCartItem(cartId);
    }

    public boolean addToCart(int userId, int productId, int quantity, String actionType) {
        return dao.addToCart(userId, productId, quantity, actionType);
    }
}
