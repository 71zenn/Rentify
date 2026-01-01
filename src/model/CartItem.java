
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zenni
 */


public class CartItem {

    private int cartId;
    private int itemId;
    private String itemName;
    private String itemType;   // BOOK / MOVIE
    private String actionType; // RENT / BUY
    private double price;

    // ✅ New features
    private String image;      // store image URL/path (e.g., "images/book1.jpg" or "https://...")
    private int quantity;      // how many of this item in cart

    public CartItem() {
        this.quantity = 1; // default quantity
    }

    // Optional convenience constructor
    public CartItem(int cartId, int itemId, String itemName, String itemType,
                    String actionType, double price, String image, int quantity) {
        this.cartId = cartId;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemType = itemType;
        this.actionType = actionType;
        this.price = price;
        this.image = image;
        setQuantity(quantity); // validate
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // ✅ New getters/setters
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    // Prevent invalid quantity (0 or negative)
    public void setQuantity(int quantity) {
        this.quantity = (quantity <= 0) ? 1 : quantity;
    }

    // ✅ Helpful methods
    public double getTotalPrice() {
        return this.price * this.quantity;
    }

    public void increaseQuantity(int amount) {
        if (amount > 0) this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        if (amount > 0) {
            this.quantity -= amount;
            if (this.quantity <= 0) this.quantity = 1;
        }
    }
}
