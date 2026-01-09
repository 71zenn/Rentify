/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.AddToCartController;
import model.CartModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AddToCart extends JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(AddToCart.class.getName());

    // --- Controller + temp user ---
    private final AddToCartController cartController = new AddToCartController();
    private int loggedInUserId = 1; // later replace with UserSession

    // --- UI components (we control, no NetBeans auto code) ---
    private JPanel BackgroundPanel;
    private JScrollPane scrlCart;
    private JPanel cartListPanel;

    private JTextField txtSearch;
    private JCheckBox chkSelectALL;
    private JButton btnDeleteSelected;

    private JLabel lblSubtotal;
    private JLabel lblTotal;

    // track items + checkboxes
    private List<CartModel> currentItems = new ArrayList<>();
    private final List<JCheckBox> itemChecks = new ArrayList<>();

    public AddToCart() {
        buildUI();
        loadCart();
    }

    private void buildUI() {
        setTitle("Rentify - Cart");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        BackgroundPanel = new JPanel(new BorderLayout());
        BackgroundPanel.setBackground(new Color(249, 250, 251));
        setContentPane(BackgroundPanel);

        // ---------- TOP HEADER ----------
        JPanel top = new JPanel();
        top.setBackground(new Color(249, 250, 251));
        top.setLayout(new BorderLayout(10, 10));
        top.setBorder(BorderFactory.createEmptyBorder(15, 20, 10, 20));

        JLabel logo = new JLabel("RENTIFY");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 22));

        JPanel searchWrap = new JPanel(new BorderLayout());
        searchWrap.setBackground(Color.WHITE);
        searchWrap.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
        searchWrap.setPreferredSize(new Dimension(350, 35));

        txtSearch = new JTextField("Search in Rentify");
        txtSearch.setBorder(null);
        txtSearch.setForeground(new Color(107, 114, 128));
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtSearch.setBackground(Color.WHITE);

        // clear placeholder when clicked
        txtSearch.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override public void focusGained(java.awt.event.FocusEvent e) {
                if (txtSearch.getText().equals("Search in Rentify")) {
                    txtSearch.setText("");
                }
            }
            @Override public void focusLost(java.awt.event.FocusEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    txtSearch.setText("Search in Rentify");
                }
            }
        });

        // Enter key search
        txtSearch.addActionListener(e -> loadCart());

        searchWrap.add(txtSearch, BorderLayout.CENTER);

        top.add(logo, BorderLayout.WEST);
        top.add(searchWrap, BorderLayout.EAST);

        BackgroundPanel.add(top, BorderLayout.NORTH);

        // ---------- CENTER (LEFT CART + RIGHT SUMMARY) ----------
        JPanel center = new JPanel(new BorderLayout(15, 10));
        center.setBackground(new Color(249, 250, 251));
        center.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        BackgroundPanel.add(center, BorderLayout.CENTER);

        // ===== LEFT SIDE =====
        JPanel leftSide = new JPanel();
        leftSide.setBackground(new Color(249, 250, 251));
        leftSide.setLayout(new BorderLayout(0, 10));

        // Select all bar
        JPanel selectBar = new JPanel(new BorderLayout());
        selectBar.setBackground(Color.WHITE);
        selectBar.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        selectBar.setPreferredSize(new Dimension(0, 40));

        chkSelectALL = new JCheckBox("SELECT ALL");
        chkSelectALL.setBackground(Color.WHITE);
        chkSelectALL.setForeground(new Color(128, 126, 126));
        chkSelectALL.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        chkSelectALL.addActionListener(e -> toggleSelectAll());

        btnDeleteSelected = new JButton("Delete");
        btnDeleteSelected.setFocusPainted(false);
        btnDeleteSelected.addActionListener(e -> deleteSelected());

        selectBar.add(chkSelectALL, BorderLayout.WEST);
        selectBar.add(btnDeleteSelected, BorderLayout.EAST);

        leftSide.add(selectBar, BorderLayout.NORTH);

        // Scroll + list panel
        cartListPanel = new JPanel();
        cartListPanel.setLayout(new BoxLayout(cartListPanel, BoxLayout.Y_AXIS));
        cartListPanel.setBackground(Color.WHITE);

        scrlCart = new JScrollPane(cartListPanel);
        scrlCart.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        scrlCart.getVerticalScrollBar().setUnitIncrement(16);
        scrlCart.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        leftSide.add(scrlCart, BorderLayout.CENTER);

        // ===== RIGHT SIDE (Order summary) =====
        JPanel rightSide = new JPanel();
        rightSide.setPreferredSize(new Dimension(360, 0));
        rightSide.setBackground(new Color(232, 241, 253));
        rightSide.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        rightSide.setLayout(new BoxLayout(rightSide, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Order Summary");
        title.setFont(new Font("Segoe UI", Font.BOLD, 14));

        lblSubtotal = new JLabel("Subtotal (0 items)");
        lblTotal = new JLabel("Rs = 0");

        lblSubtotal.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 13));

        JButton btnCheckout = new JButton("PROCEED TO CHECKOUT");
        btnCheckout.setBackground(new Color(13, 106, 210));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setFocusPainted(false);

        rightSide.add(title);
        rightSide.add(lblSubtotal);
        rightSide.add(lblTotal);
        rightSide.add(Box.createVerticalStrut(20));
        rightSide.add(btnCheckout);

        // Put left + right into center
        center.add(leftSide, BorderLayout.CENTER);
        center.add(rightSide, BorderLayout.EAST);
    }

    private void loadCart() {
        String search = txtSearch.getText();
        if (search == null || search.trim().isEmpty() || "Search in Rentify".equalsIgnoreCase(search)) {
            search = "";
        }

        currentItems = cartController.getCartItems(loggedInUserId, search);

        cartListPanel.removeAll();
        itemChecks.clear();

        int subtotal = 0;

        for (CartModel item : currentItems) {
            cartListPanel.add(createCartRow(item));
            cartListPanel.add(Box.createVerticalStrut(10));
            subtotal += item.getProductPrice() * item.getQuantity();
        }

        lblSubtotal.setText("Subtotal (" + currentItems.size() + " items)");
        lblTotal.setText("Rs = " + subtotal);

        cartListPanel.revalidate();
        cartListPanel.repaint();
    }

    private JPanel createCartRow(CartModel item) {
        JPanel row = new JPanel(new BorderLayout(12, 10));
        row.setBackground(Color.WHITE);
        row.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        // LEFT: checkbox
        JCheckBox cb = new JCheckBox();
        cb.setBackground(Color.WHITE);
        itemChecks.add(cb);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 15));
        left.setBackground(Color.WHITE);
        left.add(cb);
        row.add(left, BorderLayout.WEST);

        // CENTER: name + meta
        JPanel center = new JPanel();
        center.setBackground(Color.WHITE);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel name = new JLabel(item.getProductName());
        name.setFont(new Font("Segoe UI", Font.BOLD, 12));

        JLabel meta = new JLabel(item.getProductType() + " | " + item.getProductForm() + " | " + item.getActionType());
        meta.setForeground(new Color(120, 120, 120));

        center.add(name);
        center.add(Box.createVerticalStrut(5));
        center.add(meta);

        row.add(center, BorderLayout.CENTER);

        // RIGHT: price + qty + buttons
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 12));
        right.setBackground(Color.WHITE);

        JLabel price = new JLabel("Rs. " + item.getProductPrice());
        JLabel qty = new JLabel("Qty: " + item.getQuantity());

        JButton minus = new JButton("-");
        JButton plus = new JButton("+");
        JButton del = new JButton("Delete");

        minus.addActionListener(e -> {
            cartController.decreaseQty(item.getCartId(), item.getQuantity());
            loadCart();
        });

        plus.addActionListener(e -> {
            cartController.increaseQty(item.getCartId(), item.getQuantity());
            loadCart();
        });

        del.addActionListener(e -> {
            cartController.deleteItem(item.getCartId());
            loadCart();
        });

        right.add(price);
        right.add(minus);
        right.add(qty);
        right.add(plus);
        right.add(del);

        row.add(right, BorderLayout.EAST);

        return row;
    }

    private void toggleSelectAll() {
        boolean select = chkSelectALL.isSelected();
        for (JCheckBox cb : itemChecks) {
            cb.setSelected(select);
        }
    }

    private void deleteSelected() {
        for (int i = 0; i < currentItems.size(); i++) {
            if (i < itemChecks.size() && itemChecks.get(i).isSelected()) {
                cartController.deleteItem(currentItems.get(i).getCartId());
            }
        }
        loadCart();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddToCart().setVisible(true));
    }
}



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        Rentifylogo = new javax.swing.JLabel();
        Librarybutton = new javax.swing.JButton();
        Booksbutton = new javax.swing.JButton();
        Moviesbutton = new javax.swing.JButton();
        Newestbutton = new javax.swing.JButton();
        Supportbutton = new javax.swing.JButton();
        Profileicon = new javax.swing.JLabel();
        CartIcon = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        Search_Icon = new javax.swing.JLabel();
        Mainsearchbar = new javax.swing.JTextField();
        pSelectALLBar = new javax.swing.JPanel();
        chkSelectALL = new javax.swing.JCheckBox();
        Cartbox = new javax.swing.JPanel();
        Cartbox1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        HPimg = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        Cartbox2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        Tslimg = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        Cartbox3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        Hamletimg = new javax.swing.JLabel();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jButton9 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        Cartbox4 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        HPimg1 = new javax.swing.JLabel();
        jCheckBox4 = new javax.swing.JCheckBox();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(null);

        jPanel1.setBackground(new java.awt.Color(249, 250, 251));
        jPanel1.setMinimumSize(new java.awt.Dimension(1280, 720));
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 720));

        Rentifylogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Rentifylogo.png"))); // NOI18N

        Librarybutton.setBackground(new java.awt.Color(232, 241, 253));
        Librarybutton.setText("Library");
        Librarybutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Librarybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LibrarybuttonActionPerformed(evt);
            }
        });

        Booksbutton.setBackground(new java.awt.Color(232, 241, 253));
        Booksbutton.setText("Books");
        Booksbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Booksbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BooksbuttonActionPerformed(evt);
            }
        });

        Moviesbutton.setBackground(new java.awt.Color(232, 241, 253));
        Moviesbutton.setText("Movies");
        Moviesbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Moviesbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoviesbuttonActionPerformed(evt);
            }
        });

        Newestbutton.setBackground(new java.awt.Color(232, 241, 253));
        Newestbutton.setText("Newest");
        Newestbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Newestbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewestbuttonActionPerformed(evt);
            }
        });

        Supportbutton.setBackground(new java.awt.Color(232, 241, 253));
        Supportbutton.setText("Support");
        Supportbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Supportbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupportbuttonActionPerformed(evt);
            }
        });

        Profileicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profile icon.png"))); // NOI18N

        CartIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cart_icon.png"))); // NOI18N

        jSeparator1.setForeground(new java.awt.Color(209, 213, 219));

        Search_Icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search_Icon.png"))); // NOI18N

        Mainsearchbar.setBackground(new java.awt.Color(242, 242, 242));
        Mainsearchbar.setForeground(new java.awt.Color(107, 114, 128));
        Mainsearchbar.setText("Search in Rentify");
        Mainsearchbar.setBorder(null);
        Mainsearchbar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                MainsearchbarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                MainsearchbarFocusLost(evt);
            }
        });
        Mainsearchbar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MainsearchbarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Mainsearchbar, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Search_Icon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Search_Icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Mainsearchbar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pSelectALLBar.setPreferredSize(new java.awt.Dimension(35, 40));
        pSelectALLBar.setLayout(new java.awt.BorderLayout());

        chkSelectALL.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        chkSelectALL.setForeground(new java.awt.Color(128, 126, 126));
        chkSelectALL.setText("SELECT ALL (ITEM(S))");
        pSelectALLBar.add(chkSelectALL, java.awt.BorderLayout.PAGE_END);

        Cartbox.setBackground(new java.awt.Color(255, 255, 255));
        Cartbox.setLayout(new javax.swing.BoxLayout(Cartbox, javax.swing.BoxLayout.Y_AXIS));

        Cartbox1.setBackground(new java.awt.Color(255, 255, 255));
        Cartbox1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        HPimg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Harry.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(HPimg, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HPimg, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Harry Potter ");

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("No Brand");

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("4 iems(s) left");

        jLabel4.setText("Rs.2199");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("1");

        jButton2.setText("+");

        jButton7.setText("-");

        javax.swing.GroupLayout Cartbox1Layout = new javax.swing.GroupLayout(Cartbox1);
        Cartbox1.setLayout(Cartbox1Layout);
        Cartbox1Layout.setHorizontalGroup(
            Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCheckBox1)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(165, 165, 165)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
        );
        Cartbox1Layout.setVerticalGroup(
            Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox1Layout.createSequentialGroup()
                .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jCheckBox1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Cartbox1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Cartbox1Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox1Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Cartbox1Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Cartbox.add(Cartbox1);

        Cartbox2.setBackground(new java.awt.Color(255, 255, 255));
        Cartbox2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        Tslimg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/The secret library.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(Tslimg, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Tslimg, javax.swing.GroupLayout.PREFERRED_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("The Secret Library");

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("No Brand");

        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("5 iems(s) left");

        jLabel9.setText("Rs.1499");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("1");

        jButton8.setText("-");

        jButton10.setText("+");

        javax.swing.GroupLayout Cartbox2Layout = new javax.swing.GroupLayout(Cartbox2);
        Cartbox2.setLayout(Cartbox2Layout);
        Cartbox2Layout.setHorizontalGroup(
            Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jCheckBox2)
                .addGap(18, 18, 18)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37))
                    .addGroup(Cartbox2Layout.createSequentialGroup()
                        .addGroup(Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        Cartbox2Layout.setVerticalGroup(
            Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox2Layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jCheckBox2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(Cartbox2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel6)
                        .addGroup(Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Cartbox2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addGroup(Cartbox2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel9)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)
                                .addGap(16, 16, 16)))))
                .addContainerGap())
        );

        Cartbox.add(Cartbox2);

        Cartbox3.setBackground(new java.awt.Color(255, 255, 255));
        Cartbox3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        Hamletimg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Hamlet.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(Hamletimg)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Hamletimg, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel12.setText("Hamlet");

        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("ebook");

        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("No Brand");

        jLabel15.setText("Rs.1499");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setText("1");

        jButton9.setText("-");

        jButton11.setText("+");

        javax.swing.GroupLayout Cartbox3Layout = new javax.swing.GroupLayout(Cartbox3);
        Cartbox3.setLayout(Cartbox3Layout);
        Cartbox3Layout.setHorizontalGroup(
            Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox3Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(jCheckBox3)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addGroup(Cartbox3Layout.createSequentialGroup()
                                .addGroup(Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(Cartbox3Layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox3Layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(50, 50, 50)))
                                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(38, 38, 38)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(37, 37, 37)
                                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34))))
                    .addGroup(Cartbox3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(jLabel12)
                        .addGap(58, 58, 58))))
        );
        Cartbox3Layout.setVerticalGroup(
            Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jCheckBox3)
                .addGap(57, 57, 57))
            .addGroup(Cartbox3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox3Layout.createSequentialGroup()
                        .addGroup(Cartbox3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel13)
                        .addGap(23, 23, 23))))
        );

        Cartbox.add(Cartbox3);

        Cartbox4.setBackground(new java.awt.Color(255, 255, 255));
        Cartbox4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        HPimg1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Harry.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(HPimg1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HPimg1, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel23.setText("Harry Potter ");

        jLabel24.setForeground(new java.awt.Color(153, 153, 153));
        jLabel24.setText("No Brand");

        jLabel25.setForeground(new java.awt.Color(153, 153, 153));
        jLabel25.setText("4 iems(s) left");

        jLabel26.setText("Rs.2199");

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setText("1");

        jButton3.setText("+");

        jButton12.setText("-");

        javax.swing.GroupLayout Cartbox4Layout = new javax.swing.GroupLayout(Cartbox4);
        Cartbox4.setLayout(Cartbox4Layout);
        Cartbox4Layout.setHorizontalGroup(
            Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jCheckBox4)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(165, 165, 165)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(37, 37, 37)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42))))
        );
        Cartbox4Layout.setVerticalGroup(
            Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Cartbox4Layout.createSequentialGroup()
                .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jCheckBox4)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(Cartbox4Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addGroup(Cartbox4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(Cartbox4Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(jLabel24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Cartbox4Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(Cartbox4Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel25)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Cartbox.add(Cartbox4);

        jPanel4.setBackground(new java.awt.Color(232, 241, 253));

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Order Summary");

        jLabel17.setText("Subtotal (0 items)");

        jLabel18.setText("Shipping Fee");

        jLabel19.setText("Rs.0");

        jLabel20.setText("Rs.0");

        jTextField1.setBackground(new java.awt.Color(248, 248, 248));
        jTextField1.setForeground(new java.awt.Color(174, 156, 156));
        jTextField1.setText("Enter Voucher Code");
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));

        jButton1.setBackground(new java.awt.Color(13, 106, 210));
        jButton1.setText("APPLY");

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setText("Total");

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel22.setText("Rs = 0");

        jTextField2.setBackground(new java.awt.Color(13, 106, 210));
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("PROCEED TO CHECKOUT(0)");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(202, 202, 202)
                                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jTextField1)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)))
                        .addGap(0, 3, Short.MAX_VALUE)))
                .addGap(19, 19, 19))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel20))
                .addGap(32, 32, 32)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(Rentifylogo)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(Librarybutton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(Booksbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(Moviesbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(Newestbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(Supportbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                        .addComponent(Profileicon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(68, 68, 68))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(CartIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(116, 116, 116))))
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(108, 108, 108)
                        .addComponent(pSelectALLBar, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(Cartbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Rentifylogo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(Profileicon, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Librarybutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Booksbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Moviesbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Newestbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Supportbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(CartIcon))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pSelectALLBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(Cartbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1290, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LibrarybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LibrarybuttonActionPerformed
        javax.swing.JFrame page = new javax.swing.JFrame("Library");
        page.setSize(800, 600);
        page.setLocationRelativeTo(this);
        page.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        page.setVisible(true);   // TODO add your handling code here:
    }//GEN-LAST:event_LibrarybuttonActionPerformed

    private void BooksbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BooksbuttonActionPerformed
        javax.swing.JFrame page = new javax.swing.JFrame("Books");
        page.setSize(800, 600);
        page.setLocationRelativeTo(this);
        page.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        page.setVisible(true);
    }//GEN-LAST:event_BooksbuttonActionPerformed

    private void MoviesbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoviesbuttonActionPerformed
        javax.swing.JFrame page = new javax.swing.JFrame("Movies");
        page.setSize(800, 600);
        page.setLocationRelativeTo(this);
        page.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        page.setVisible(true);
    }//GEN-LAST:event_MoviesbuttonActionPerformed

    private void NewestbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewestbuttonActionPerformed
        javax.swing.JFrame page = new javax.swing.JFrame("Newest");
        page.setSize(800, 600);
        page.setLocationRelativeTo(this);
        page.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        page.setVisible(true);    // TODO add your handling code here:
    }//GEN-LAST:event_NewestbuttonActionPerformed

    private void SupportbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupportbuttonActionPerformed
        javax.swing.JFrame page = new javax.swing.JFrame("Support");
        page.setSize(800, 600);
        page.setLocationRelativeTo(this);
        page.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        page.setVisible(true);  // TODO add your handling code here:
    }//GEN-LAST:event_SupportbuttonActionPerformed

    private void MainsearchbarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MainsearchbarFocusGained
        if (Mainsearchbar.getText().equals("Search")) {
            Mainsearchbar.setText("");
            Mainsearchbar.setForeground(new java.awt.Color(0, 0, 0));
        }
    }//GEN-LAST:event_MainsearchbarFocusGained

    private void MainsearchbarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MainsearchbarFocusLost
        if (Mainsearchbar.getText().trim().isEmpty()) {
            Mainsearchbar.setText("Search");
            Mainsearchbar.setForeground(new java.awt.Color(107, 114, 128));
        }
    }//GEN-LAST:event_MainsearchbarFocusLost

    private void MainsearchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainsearchbarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MainsearchbarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Cart().setVisible(true));
    }
    
    
    
    
    private void doCheckout() {
    if (loggedInUserId == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please login again.");
        return;
    }

    java.util.List<CartItem> cartItems = cartController.getCartItems(loggedInUserId);

    if (cartItems == null || cartItems.isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Your cart is empty.");
        return;
    }

    int confirm = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "Checkout " + cartItems.size() + " item(s)?",
            "Confirm Checkout",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (confirm != javax.swing.JOptionPane.YES_OPTION) return;

    cartController.checkoutCart(cartItems, loggedInUserId);

    javax.swing.JOptionPane.showMessageDialog(this, "Checkout successful!");
    loadCartItems(); // refresh UI (cart should be empty now)

    // Optional: open purchase history after checkout
    // new Purchasehistory().setVisible(true);
    // dispose();
}

    private void loadCartItems() {

    Cartbox.removeAll(); // clear old UI
    if (loggedInUserId == -1) {
        javax.swing.JOptionPane.showMessageDialog(this, "Please login again.");
        dispose();
        return;
    }

    Cartbox.removeAll();

    var cartItems = cartController.getCartItems(loggedInUserId);

    for (CartItem item : cartItems) {
    Cartbox.add(createCartItemPanel(item));
    Cartbox.add(Box.createVerticalStrut(10));  // spacing between cards
}

    int count = cartItems.size();
jTextField2.setText("PROCEED TO CHECKOUT(" + count + ")");
jLabel17.setText("Subtotal (" + count + " items)");

    Cartbox.revalidate();
    Cartbox.repaint();



}
    
    
   private javax.swing.JPanel createCartItemPanel(CartItem item) {

    JPanel row = new JPanel(new BorderLayout(15, 10));
    row.setBackground(Color.WHITE);
    row.setBorder(BorderFactory.createLineBorder(new Color(204, 204, 204)));
    row.setMaximumSize(new Dimension(950, 150));   // similar height like static
    row.setPreferredSize(new Dimension(950, 150));

    // ✅ LEFT: checkbox + image
    JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 20));
    left.setBackground(Color.WHITE);

    JCheckBox cb = new JCheckBox();
    cb.setBackground(Color.WHITE);

    JLabel img = new JLabel();
    img.setPreferredSize(new Dimension(99, 122));

    try {
        // DB image example: "/icons/Harry.png"
        img.setIcon(new ImageIcon(getClass().getResource(item.getImage())));
    } catch (Exception e) {
        img.setText("No Image");
    }

    left.add(cb);
    left.add(img);
    row.add(left, BorderLayout.WEST);

    // ✅ CENTER: name + brand + stock/type
    JPanel center = new JPanel();
    center.setBackground(Color.WHITE);
    center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
    center.setBorder(new EmptyBorder(15, 0, 0, 0));

    JLabel name = new JLabel(item.getItemName());
    name.setFont(new Font("Segoe UI", Font.BOLD, 12));

    JLabel brand = new JLabel("No Brand");
    brand.setForeground(new Color(153, 153, 153));

    JLabel type = new JLabel(item.getItemType() + " | " + item.getActionType());
    type.setForeground(new Color(153, 153, 153));

    center.add(name);
    center.add(Box.createVerticalStrut(10));
    center.add(brand);
    center.add(Box.createVerticalStrut(5));
    center.add(type);

    row.add(center, BorderLayout.CENTER);

    // ✅ RIGHT: price + qty + +/- + remove
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 45));
    right.setBackground(Color.WHITE);

    JLabel price = new JLabel("Rs. " + item.getPrice());

    JButton minus = new JButton("-");
    JLabel qty = new JLabel(String.valueOf(item.getQuantity()));
    qty.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    JButton plus = new JButton("+");

    JButton removeBtn = new JButton("Remove");
    removeBtn.addActionListener(e -> {
        cartController.removeItemFromCart(item.getCartId());
        loadCartItems();
    });

    right.add(price);
    right.add(minus);
    right.add(qty);
    right.add(plus);
    right.add(removeBtn);

    row.add(right, BorderLayout.EAST);

    return row;
}

    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Booksbutton;
    private javax.swing.JLabel CartIcon;
    private javax.swing.JPanel Cartbox;
    private javax.swing.JPanel Cartbox1;
    private javax.swing.JPanel Cartbox2;
    private javax.swing.JPanel Cartbox3;
    private javax.swing.JPanel Cartbox4;
    private javax.swing.JLabel HPimg;
    private javax.swing.JLabel HPimg1;
    private javax.swing.JLabel Hamletimg;
    private javax.swing.JButton Librarybutton;
    private javax.swing.JTextField Mainsearchbar;
    private javax.swing.JButton Moviesbutton;
    private javax.swing.JButton Newestbutton;
    private javax.swing.JLabel Profileicon;
    private javax.swing.JLabel Rentifylogo;
    private javax.swing.JLabel Search_Icon;
    private javax.swing.JButton Supportbutton;
    private javax.swing.JLabel Tslimg;
    private javax.swing.JCheckBox chkSelectALL;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JPanel pSelectALLBar;
    // End of variables declaration//GEN-END:variables
}
