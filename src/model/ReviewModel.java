/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zenni
 */
public class ReviewModel {
        private int reviewID;
        private String userName; 
        private String productReview;
        private String profileImage; 
        private float productRating;
               
        
        public ReviewModel(int reviewID, String userName, String productReview, String profileImage, float productRating){
            this.reviewID = reviewID;
            this.userName = userName;
            this.productReview = productReview; 
            this.profileImage = profileImage;
            this.productRating = productRating;
        }
        
        public ReviewModel(String userName, String productReview, String profileImage,float productRating){
            this.userName = userName;
            this.productReview = productReview; 
            this.profileImage = profileImage;
            this.productRating = productRating;
        }
        
        public int getReviewID(){
            return reviewID;
        }
        
        public int setReviewID(int reviewID){
            return reviewID;
        }
        
        public String getUserName() {
            return userName;
        }
        
        public void setUserName(String userName) {
            this.userName = userName;
        }
        
        public String getProductReview() {
            return productReview;
        }
        
        public void setProductReview(String productReview) {
            this.productReview = productReview;
        }
        
        public void setProductImage(String productReview) {
            this.productReview = productReview;
        }
        
        public String getProfileImage(){
            return profileImage; 
        }
        
        public void setProfileImage(String profileImage) {
            this.profileImage = profileImage;
        }
        
        public float getProductRating() {
            return productRating;
        }
        
        public void ProductRating(float productRating) {
            this.productRating = productRating;
        }
        
        
}
