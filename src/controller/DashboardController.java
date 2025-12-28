package controller;

import javax.swing.JFileChooser;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import userdata.ProductDao;
import model.ProductModel;
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
        dashboardView.addProductModelListener(new AddProductListener());
        
        dashboardView.getProductImage().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
            openFileChooser();
            }
            });
        }
**/
    }
}
