package controller;

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
      /**
        dashboardView.addProductModelListener(new AddProductModelListener());
        
        dashboardView.getProductImage().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                openFileChooser();
            }
            });
        }
**/
        loadAllProducts();
    }
    
    private void loadAllProducts(){
        List<ProductModel> products = productDao.getAllProducts();
        JPanel panel = dashboardView.getProductPanel();
        
        panel.removeAll();
        
        for (ProductModel product : products){
            ProductPanel card = new ProductPanel();
            panel.add(card);    
        }
        
        panel.revalidate();
        panel.repaint();
        
    }
}
