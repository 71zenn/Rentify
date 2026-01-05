/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Dimension;
import java.util.List;
import model.ProductModel;
import userdata.ProductDao;
import view.SpecificProductPage;
import view.ProductPagePanel;
import view.ProductPanel;
import view.SpecificProductPage;


/**
 *
 * @author zenni
 */
public class ProductpageController {
    private final ProductDao productDao = new ProductDao();
    private final SpecificProductPage productpage;
    
    public ProductpageController(SpecificProductPage productpage, int productId) {
        this.productpage = productpage;
        loadProductById(productId);
        
    }
    
    public List<ProductModel>getAllProducts(){
        return productDao.getAllProducts();
        
    }
    
    public void loadProductById(int id) {
    javax.swing.JPanel productListPanel = productpage.getProductPanel();

    productListPanel.removeAll();
    productListPanel.setLayout(new java.awt.GridLayout(1, 1, 0, 0));

    try {
        ProductModel product = productDao.getProductById(id);

        if (product != null) {
            ProductPagePanel card = new ProductPagePanel(); // <-- use this class
            card.setProduct(product);
            card.setPreferredSize(new java.awt.Dimension(880, 220));
            productListPanel.add(card);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    productListPanel.revalidate();
    productListPanel.repaint();
}
 
}
