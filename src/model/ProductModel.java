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
        private String productType;
        private String productForm; 
        private int productPrice; 
        private int product_ID;
        private int productQuantity;
        
        
        public ProductModel(String productName, String productImage , int productPrice, String productSynopsis, String productType, String productForm, int productQuantity){
            this.productName = productName;
            this.productImage = "src/pictures/" + productImage;
            this.productPrice = productPrice;
            this.productSynopsis = productSynopsis;
            this.productType = productType;
            this.productForm = productForm;
            this.productQuantity = productQuantity;
        }

        // Used when creating a new product (no ID yet)
        public ProductModel(int product_ID, String productName, String productImage , int productPrice, String productSynopsis, String productType, String productForm, int productQuantity) {
            this.product_ID = product_ID;
            this.productName = productName;
            this.productImage = productImage;
            this.productPrice = productPrice;
            this.productSynopsis = productSynopsis;
            this.productType = productType; //true for book and false for movie
            this.productForm = productForm; //true for digital and false for phyical copy !!movies are always true no exception!!
            this.productQuantity = productQuantity;
        }
        
        public int getProductID() {
            return product_ID;
        }
        
        public void setProductID(int product_ID) {
            this.product_ID = product_ID;
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
            this.productImage = "src/pictures/" + productImage;
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
        
        public String getProductType() {
            return productType;
        }
        
        public void setProductType(String productType) {
            this.productType = productType;
        }
        
        public String getProductForm() {
            return productForm;
        }
        
        public void setProductForm(String productForm) {
            this.productForm = productForm;
        }
        
        public int getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(int productQuantity) {
            this.productQuantity = productQuantity;
        }
}
 