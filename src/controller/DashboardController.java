package controller;

import java.awt.Dimension;
import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import userdata.ProductDao;
import model.ProductModel;
import view.ProductPanel;
import view.UserDashboard;

/**
*
* @author User
*/

public class DashboardController {
    private final ProductDao productDao = new ProductDao();
    private final UserDashboard dashboardView;
    
    public DashboardController(UserDashboard dashboardView) {
        this.dashboardView = dashboardView;
        getAllProducts();
        loadAllProducts();
        
    }
    
    public List<ProductModel>getAllProducts(){
        return productDao.getAllProducts();
        
    }
     /**
      * //method to be made in other file!!
    public List<ProductModel> getProductsByCategory(String category) {
        return productDao.getProductsByCategory(category);
    } 
     
        dashboardView.addProductModelListener(new AddProductModelListener());
        
        dashboardView.getProductImage().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openFileChooser();
            }
            });
        }
**/
    
    public void loadAllProducts() {
        javax.swing.JPanel productListPanel = dashboardView.getProductPanel();

        productListPanel.removeAll();
        productListPanel.setLayout(new java.awt.GridLayout(0, 4, 20, 20));

        try {
            List<ProductModel> products = productDao.getAllProducts(); 
            for (model.ProductModel product : products) {
                ProductPanel card = new ProductPanel();
                card.setProduct(product);
                card.setPreferredSize(new Dimension(150, 170));
                productListPanel.add(card);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        productListPanel.revalidate();
        productListPanel.repaint();
    }
} 
