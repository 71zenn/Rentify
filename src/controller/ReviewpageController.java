/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author zenni
 */
import userdata.ReviewDao;
import model.ProductModel;
import model.ReviewModel;
import view.ReviewPage;      // your Swing/JPanel/JFrame that shows reviews

import java.util.List;
import javax.swing.BoxLayout;
import userdata.ReviewDao;
import view.ReviewPage;
import view.reviewPanel;
import view.reviewPanel;

public class ReviewpageController {

    private final ReviewDao reviewDao = new ReviewDao();
    private final ReviewPage view;
    private final int productId;
    private final int userId;
    private final String userName;
    private final String profileImage;

    private ReviewModel currentReview;  // null if user has no review yet

    public ReviewpageController(ReviewPage view, int productId, int userId, String userName, String profileImage, String productName) {
        this.view = view;
        this.productId = productId;
        this.userId = userId;
        this.userName = userName;
        this.profileImage = profileImage;

        // show product name at top
        view.getProductNameLabel().setText(productName);
        
        loadUserReview();
        loadAllReviews();
        initListeners();
    }

    private void loadUserReview() {
        currentReview = reviewDao.getReviewByUserAndProduct(userId, productId);
        if (currentReview != null) {
            view.setRatingText(Float.toString(currentReview.getProductRating()));
            view.setReviewText(currentReview.getProductReview());
        } else {
            view.setRatingText("");
            view.setReviewText("");
        }
    }

    private void loadAllReviews() {
        List<ReviewModel> reviews = reviewDao.getReviewsForProduct(productId);

        var listPanel = view.getReviewsListPanel();
        listPanel.removeAll();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        for (ReviewModel r : reviews) {
            reviewPanel p = new reviewPanel();
            p.setReview(r);
            listPanel.add(p);
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private void initListeners() {
        view.getAddButton().addActionListener(e -> saveOrUpdateReview());
    }

    private void saveOrUpdateReview() {
    String reviewText = view.getReviewText();
    float rating;

    try {
        rating = Float.parseFloat(view.getRatingText());
    } catch (NumberFormatException ex) {
        javax.swing.JOptionPane.showMessageDialog(
                view,
                "Rating must be a number.",
                "Error",
                javax.swing.JOptionPane.ERROR_MESSAGE
        );
        return;
    }

    if (currentReview == null) {
        // create new
        ReviewModel review = new ReviewModel(
                userId,       // controller field
                userName,     // controller field
                reviewText,
                profileImage, // controller field
                rating
        );
        reviewDao.addReview(productId, review);

        // reload to get id
        currentReview = reviewDao.getReviewByUserAndProduct(userId, productId);

    } else {
        // update existing
        currentReview.setProductReview(reviewText);
        currentReview.setProductRating(rating);
        currentReview.setProfileImage(profileImage);
        reviewDao.updateReview(currentReview);
    }

    loadAllReviews();
}


    // Optionally call this from a "Delete" button in ReviewPage
    public void deleteReview() {
        if (currentReview != null) {
            reviewDao.deleteReview(currentReview.getReviewID());
            currentReview = null;
            view.setRatingText("");
            view.setReviewText("");
            loadAllReviews();
        }
    }

}
