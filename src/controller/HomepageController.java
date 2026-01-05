/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Dimension;
import java.util.List;
import model.ProductModel;
import userdata.ProductDao;
import view.HomePage;
import view.ProductPanel;
import view.UserDashboard;

/**
 *
 * @author zenni
 */
public class HomepageController {
    private final ProductDao productDao = new ProductDao();
    private final HomePage dashboardView;
    
    public HomepageController(HomePage dashboardView) {
        this.dashboardView = dashboardView;
        getAllProducts();
        loadAllProducts();
        
    }
    
    public List<ProductModel>getAllProducts(){
        return productDao.getAllProducts();
        
    }
     
    
    public void loadAllProducts() {
    javax.swing.JPanel productListPanel = dashboardView.getProductPanel();

    productListPanel.removeAll();
    productListPanel.setLayout(new java.awt.GridLayout(0, 4, 20, 20 ));
    
    try {
        List<ProductModel> products = productDao.getAllProducts();
        for (ProductModel product : products) {
            ProductPanel card = new ProductPanel();
            card.setProduct(product);
            card.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
            card.setPreferredSize(new Dimension(150, 170));

            final int pid = product.getProductID();   // capture id

            card.addProductClickListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    // open specific product page by id
                    view.SpecificProductPage page = new view.SpecificProductPage(pid);
                    page.setVisible(true);
                }
            });
            
            productListPanel.add(card);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    productListPanel.revalidate();
    productListPanel.repaint();
}
}
