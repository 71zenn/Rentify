package model;

public class CartModel {
    private int cartId;
    private int userId;
    private int productId;
    private int quantity;
    private String actionType;

    private String productName;
    private String productImage;
    private int productPrice;
    private String productType;
    private String productForm;

    public CartModel(int cartId, int userId, int productId, int quantity, String actionType,
                     String productName, String productImage, int productPrice,
                     String productType, String productForm) {
        this.cartId = cartId;
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
        this.actionType = actionType;
        this.productName = productName;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productForm = productForm;
    }

    public int getCartId() { return cartId; }
    public int getUserId() { return userId; }
    public int getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public String getActionType() { return actionType; }

    public String getProductName() { return productName; }
    public String getProductImage() { return productImage; }
    public int getProductPrice() { return productPrice; }
    public String getProductType() { return productType; }
    public String getProductForm() { return productForm; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
