/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zenni
 */



  public class ProductModel {
        private String productName; 
        private String productImage;
        private String productSynopsis;
        private Boolean productType;
        private Boolean productForm;
        private int productPrice; 
        
        
        public ProductModel(String productName, String productImage , int productPrice, String productSynopsis, Boolean productType, Boolean productForm){
            this.productName = productName;
            this.productImage = productImage;
            this.productPrice = productPrice;
            this.productSynopsis = productSynopsis;
            this.productType = productType;
            this.productForm = productForm;
        }
        
        public String getProductName() {
            return productName;
        }
        
        public void setProductName(String productName) {
            this.productName = productName;
        }
        
        public String getProductImage() {
            return productImage;
        }
        
        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }
        
        public int getProductPrice() {
            return productPrice;
        }
        
        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }
        
        public String getProductSynopsis() {
            return productSynopsis;
        }
        
        public void setProductSynopsis(String productSynopsis) {
            this.productSynopsis = productSynopsis;
        }
        
        public Boolean getProductType() {
            return productType;
        }
        
        public void setProductSynopsis(Boolean productType) {
            this.productType = productType;
        }
        
        public Boolean getProductForm() {
            return productForm;
        }
        
        public void setProductForm(Boolean productForm) {
            this.productForm = productForm;
        }
        
}
 