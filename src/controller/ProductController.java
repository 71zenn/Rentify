/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;
import java.util.List;
import model.ProductModel;
import userdata.ProductDao;
/**
 *
 * @author sthaa
 */
public class ProductController {
     private ProductDao dao;

    public ProductController() {
        dao = new ProductDao();
    }

    public void addProduct(ProductModel product) {
        dao.createProduct(product);
    }

    public List<ProductModel> getAllProducts() {
        return dao.getAllProducts();
    }

    public ProductModel getProductById(int productId) {
        return dao.getProductById(productId);
    }

    public void updateProduct(ProductModel product) {
        dao.updateProduct(product);
    }

    public void deleteProduct(int productId) {
        dao.deleteProduct(productId);
    }    
}
