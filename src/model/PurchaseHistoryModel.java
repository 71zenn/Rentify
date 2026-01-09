/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author Dell
 */
public class PurchaseHistoryModel {
    
     private final int orderId;
    private final Timestamp purchaseDate;
    private final String itemType;
    private final double price;

    private final String productName;
    private final String productForm;
    private final String productImage; // "/pictures/xxx.jpg"

    public PurchaseHistoryModel(int orderId, Timestamp purchaseDate, String itemType, double price,
                                String productName, String productForm, String productImage) {
        this.orderId = orderId;
        this.purchaseDate = purchaseDate;
        this.itemType = itemType;
        this.price = price;
        this.productName = productName;
        this.productForm = productForm;
        this.productImage = productImage;
    }

    public int getOrderId() { return orderId; }
    public Timestamp getPurchaseDate() { return purchaseDate; }
    public String getItemType() { return itemType; }
    public double getPrice() { return price; }

    public String getProductName() { return productName; }
    public String getProductForm() { return productForm; }
    public String getProductImage() { return productImage; }
}
    

