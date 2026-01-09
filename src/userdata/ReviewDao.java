/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

import database.MySQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ReviewModel;

/**
 *
 * @author zenni
 */
public class ReviewDao {
    

    private final MySQLConnection mysql = new MySQLConnection();

    public List<ReviewModel> getReviewsForProduct(int productId) {
        List<ReviewModel> reviews = new ArrayList<>();
        Connection conn = mysql.openConnection();
        String sql = "SELECT id, product_id, user_id, user_name, " +
                     "product_review, profile_image, product_rating " +
                     "FROM reviews WHERE product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ReviewModel review = new ReviewModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("product_review"),
                        rs.getString("profile_image"),
                        rs.getFloat("product_rating")
                    );
                    reviews.add(review);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return reviews;
    }

    // Get current user's review for a product (for edit form)
    public ReviewModel getReviewByUserAndProduct(int userId, int productId) {
        ReviewModel review = null;
        Connection conn = mysql.openConnection();
        String sql = "SELECT * FROM reviews WHERE user_id = ? AND product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    review = new ReviewModel(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("product_review"),
                        rs.getString("profile_image"),
                        rs.getFloat("product_rating")
                    );
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return review;
    }

    // Insert new review
    public void addReview(int productId, ReviewModel review) {
        Connection conn = mysql.openConnection();
        
    // Option 1: explicit check
    if (getReviewByUserAndProduct(review.getUserID(), productId) != null) {
        throw new IllegalStateException("User already reviewed this product.");
    }

    String sql = "INSERT INTO reviews (user_id, product_id, user_name, review_text, profile_image, rating) "
               + "VALUES (?,?,?,?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            pstmt.setInt(2, review.getUserID());
            pstmt.setString(3, review.getUserName());
            pstmt.setString(4, review.getProductReview());
            pstmt.setString(5, review.getProfileImage());
            pstmt.setFloat(6, review.getProductRating());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // Update existing review (by id)
    public void updateReview(ReviewModel review) {
        Connection conn = mysql.openConnection();
        String sql = "UPDATE reviews SET " +
                     "product_review = ?, profile_image = ?, product_rating = ? " +
                     "WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, review.getProductReview());
            pstmt.setString(2, review.getProfileImage());
            pstmt.setFloat(3, review.getProductRating());
            pstmt.setInt(4, review.getReviewID());
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
    }

    // Delete review by id
    public void deleteReview(int reviewId) {
        Connection conn = mysql.openConnection();
        String sql = "DELETE FROM reviews WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, reviewId);
            pstmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
    }

    public float getAverageRatingForProduct(int productId) {
        float avg = 0f;
        Connection conn = mysql.openConnection();
        String sql = "SELECT AVG(product_rating) AS avg_rating " +
                     "FROM reviews WHERE product_id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    avg = rs.getFloat("avg_rating");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            mysql.closeConnection(conn);
        }
        return avg;
    }
}

