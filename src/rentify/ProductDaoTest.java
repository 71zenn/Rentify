/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rentify;

import Model.Product;
import dao.ProductDao;
/**
 *
 * @author ASUS
 */
    public class ProductDaoTest {
     public static void main(String[] args) {
       Product p = new Product("test book", "Book", "C:/Users/ASUS/OneDrive/Documents/NetBeansProjects/Rentify/src/icons/Harry_Potter.png", "Fantasy");
        
       ProductDao dao = new ProductDao();
       dao.insert(p);
    
       System.out.println("Inserted test product.");
    }
}
