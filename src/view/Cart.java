/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;
import controller.CartController;
import model.CartItem;
import userdata.UserSession;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;




/**
 *
 * @author Dell
 */
public class Cart extends javax.swing.JFrame {
    private CartController cartController;
    private JPanel contentPanel;
    private JSplitPane split;
    private int loggedInUserId;
    // --- UI selection + cart line state ---
private final java.util.Map<JCheckBox, CartLine> cbToLine = new java.util.LinkedHashMap<>();
private boolean bulkChanging = false;
private JButton btnDeleteAll;
private JPanel summaryHost;
    
private static class CartLine {
    final CartItem item;
    int qty;
    CartLine(CartItem item) {
        this.item = item;
        this.qty = item.getQuantity();
    }
}
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Cart.class.getName());

    /**
     * Creates new form Cart
     */
    public Cart() {

    initComponents();
    
    
 
    cartController = new CartController();
    loggedInUserId = UserSession.getUserId();

    setLocationRelativeTo(null);
    setResizable(true);
    
    // ===== TOP BAR (SELECT ALL + DELETE) like screenshot =====
pSelectALLBar.removeAll();
pSelectALLBar.setLayout(new BorderLayout());
pSelectALLBar.setBackground(new Color(225, 231, 236));  // light gray bar

chkSelectALL.setFont(new Font("Segoe UI", Font.PLAIN, 12));
chkSelectALL.setForeground(new Color(120, 120, 120));
chkSelectALL.setOpaque(false);

// ✅ Delete icon button
btnDeleteAll = new JButton("DELETE");
btnDeleteAll.setFont(new Font("Segoe UI", Font.PLAIN, 12));
btnDeleteAll.setForeground(new Color(120, 120, 120));
btnDeleteAll.setCursor(new Cursor(Cursor.HAND_CURSOR));
btnDeleteAll.setFocusPainted(false);
btnDeleteAll.setBorderPainted(false);
//btnDeleteAll.setPreferredSize(new Dimension(100, 75));
btnDeleteAll.setIconTextGap(6);
btnDeleteAll.setHorizontalTextPosition(SwingConstants.RIGHT);


try {
    ImageIcon raw = new ImageIcon(getClass().getResource("/pictures/trash.png"));

    // Optional: scale icon to fit nicely
    Image scaled = raw.getImage().getScaledInstance(14, 14, Image.SCALE_SMOOTH);
    ImageIcon icon = new ImageIcon(scaled);

    btnDeleteAll.setText("DELETE");
    btnDeleteAll.setIcon(icon);

    btnDeleteAll.setIconTextGap(6); // space between icon and text

    // Put icon on the LEFT, text on the RIGHT (normal)
    btnDeleteAll.setHorizontalAlignment(SwingConstants.RIGHT); // or LEFT depending on where your button sits
    btnDeleteAll.setHorizontalTextPosition(SwingConstants.RIGHT);
    btnDeleteAll.setVerticalTextPosition(SwingConstants.CENTER);

    // Make sure it has enough width so text doesn't get cut
//    btnDeleteAll.setPreferredSize(new Dimension(90, 30));

    // "label-like" styling (optional)
    btnDeleteAll.setContentAreaFilled(false);
    btnDeleteAll.setBorderPainted(false);
    btnDeleteAll.setFocusPainted(false);

} catch (Exception e) {
    e.printStackTrace();
    btnDeleteAll.setText("DELETE");
}


btnDeleteAll.setContentAreaFilled(false); // looks like label/button in screenshot
btnDeleteAll.addActionListener(e -> deleteSelectedItems());

JPanel leftBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
leftBar.setOpaque(false);
leftBar.add(chkSelectALL);

JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
rightBar.setOpaque(false);
rightBar.add(btnDeleteAll);

pSelectALLBar.add(leftBar, BorderLayout.WEST);
pSelectALLBar.add(rightBar, BorderLayout.EAST);

chkSelectALL.addActionListener(e -> {
    if (bulkChanging) return;
    boolean select = chkSelectALL.isSelected();

    bulkChanging = true;
    for (JCheckBox cb : cbToLine.keySet()) cb.setSelected(select);
    bulkChanging = false;

    updateOrderSummary();
});


    // ✅ Make "Proceed to checkout" behave like a button
        
    jTextField2.setEditable(false);
    jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            doCheckout();
        }
    });

    // ✅ Build contentPanel (center area under header)
    contentPanel = new JPanel(new BorderLayout());
    contentPanel.setOpaque(false);

    // ✅ Remove old components that NetBeans put there (cart + summary)
    jPanel1.remove(Cartbox);
    jPanel1.remove(jPanel4);

    // ✅ Wrap cart in scroll
    Cartbox.removeAll();
    Cartbox.setLayout(new BoxLayout(Cartbox, BoxLayout.Y_AXIS));
   JScrollPane cartScroll = new JScrollPane(
        Cartbox,
        JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER   // ✅ important
);
cartScroll.setBorder(null);
cartScroll.getVerticalScrollBar().setUnitIncrement(16); // smooth scroll
cartScroll.getViewport().setBackground(Color.WHITE);
cartScroll.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 12)); // 12px gap to the right
jPanel4.setBorder(BorderFactory.createEmptyBorder(0, 12, 0, 0));    // 12px gap to the left



    // ✅ Split pane 50/50
    summaryHost = buildOrderSummaryPanel();

split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, cartScroll, summaryHost);
    split.setResizeWeight(0.6);
SwingUtilities.invokeLater(() -> split.setDividerLocation(0.65));
    split.setDividerSize(6);
    split.setBorder(null);
    split.setEnabled(false);          // disables divider mouse drag
split.setDividerSize(2);          // thin line (or 0 to hide completely)
split.setBorder(null);
    split.setContinuousLayout(true);
split.setOneTouchExpandable(false);

    contentPanel.add(split, BorderLayout.CENTER);

    // ✅ Add contentPanel into jPanel1 with manual positioning (since you use null layout)
    contentPanel.setBounds(50, 235, 1240, 430); // adjust if needed
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); 
//    pSelectALLBar.setBorder(BorderFactory.createEmptyBorder(6, 12, 10, 12));
// top,left,bottom,right

    jPanel1.setLayout(null); // important if not already null
    jPanel1.add(contentPanel);

    // ✅ Make divider exact after UI shows
  

    Cartbox.setOpaque(true);
    Cartbox.setBackground(Color.WHITE);
    

    // ✅ Load DB items
    loadCartItems();

    pack();
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        Cartbox2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        Tslimg = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
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
        setPreferredSize(new java.awt.Dimension(1280, 720));
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
                        .addGap(0, 273, Short.MAX_VALUE)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(49, 49, 49)
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
                        .addGap(29, 29, 29)
                        .addGroup(Cartbox1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))))
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
                .addComponent(Tslimg, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("The Secret Library");

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
                            .addComponent(jLabel8))
                        .addContainerGap(483, Short.MAX_VALUE))))
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
                                .addGap(0, 285, Short.MAX_VALUE)
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 90, Short.MAX_VALUE)
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pSelectALLBar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(Cartbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 1280, 720);

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
        JOptionPane.showMessageDialog(this, "Please login again.");
        return;
    }

    java.util.Set<Integer> selectedIds = getSelectedCartIds();
    if (selectedIds.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least 1 item.");
        return;
    }

    // reload from DB so quantities are 100% correct
    java.util.List<CartItem> all = cartController.getCartItems(loggedInUserId);
    java.util.List<CartItem> selected = new java.util.ArrayList<>();
    for (CartItem it : all) {
        if (selectedIds.contains(it.getCartId())) selected.add(it);
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Checkout " + selected.size() + " item(s)?",
            "Confirm Checkout",
            JOptionPane.YES_NO_OPTION
    );
    if (confirm != JOptionPane.YES_OPTION) return;

    cartController.checkoutCart(selected, loggedInUserId);
    JOptionPane.showMessageDialog(this, "Checkout successful!");
    loadCartItems();
}

    private java.util.Set<Integer> getSelectedCartIds() {
    java.util.Set<Integer> ids = new java.util.HashSet<>();
    for (var entry : cbToLine.entrySet()) {
        if (entry.getKey().isSelected()) ids.add(entry.getValue().item.getCartId());
    }
    return ids;
}

    private void loadCartItems() {
    Cartbox.removeAll();
    cbToLine.clear();

    if (loggedInUserId == -1) {
        JOptionPane.showMessageDialog(this, "Please login again.");
        dispose();
        return;
    }

    java.util.List<CartItem> cartItems = cartController.getCartItems(loggedInUserId);

    for (CartItem item : cartItems) {
        Cartbox.add(createCartItemPanel(item));
        Cartbox.add(Box.createVerticalStrut(0)); // screenshot rows touch each other
    }

    // reset top controls + summary
    bulkChanging = true;
    chkSelectALL.setSelected(false);
    bulkChanging = false;

    btnDeleteAll.setEnabled(!cartItems.isEmpty());
    updateOrderSummary();

    Cartbox.revalidate();
    Cartbox.repaint();
}

    
    
  private JPanel createCartItemPanel(CartItem item) {

    JPanel row = new JPanel(new GridBagLayout());
    row.setBackground(Color.WHITE);
    row.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

    // ✅ DO NOT set a huge preferred width (causes horizontal scroll)
    row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
    row.setPreferredSize(new Dimension(10, 150)); // width ignored, height kept
    row.setAlignmentX(Component.LEFT_ALIGNMENT);

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridy = 0;
    gc.fill = GridBagConstraints.BOTH;
    gc.weighty = 1;

    // ===== 1) Checkbox column =====
    JCheckBox cb = new JCheckBox();
    cb.setBackground(Color.WHITE);

    CartLine line = new CartLine(item);
    cbToLine.put(cb, line);

    cb.addActionListener(e -> {
        if (bulkChanging) return;
        updateOrderSummary();
    });

    gc.gridx = 0;
    gc.weightx = 0;
    gc.insets = new Insets(0, 12, 0, 10);
    row.add(cb, gc);

    // ===== 2) Image column =====
    JPanel imgWrap = new JPanel();
    imgWrap.setBackground(Color.WHITE);
    imgWrap.setLayout(new BoxLayout(imgWrap, BoxLayout.Y_AXIS));

    JLabel seller = new JLabel("Rentify");
    seller.setFont(new Font("Segoe UI", Font.PLAIN, 10));
    seller.setForeground(new Color(160, 160, 160));

    JLabel img = new JLabel();
    img.setPreferredSize(new Dimension(120, 90));
    img.setMinimumSize(new Dimension(120, 90));
    img.setMaximumSize(new Dimension(120, 90));
    img.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));

    try {
        img.setIcon(new ImageIcon(getClass().getResource(item.getImage())));
    } catch (Exception ex) {
        img.setText("No Image");
        img.setHorizontalAlignment(SwingConstants.CENTER);
    }

    imgWrap.add(seller);
    imgWrap.add(Box.createVerticalStrut(6));
    imgWrap.add(img);

    gc.gridx = 1;
    gc.weightx = 0;
    gc.insets = new Insets(10, 0, 10, 10);
    row.add(imgWrap, gc);

    // ===== 3) Title/Info column =====
    JPanel info = new JPanel();
    info.setBackground(Color.WHITE);
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

    JLabel title = new JLabel(item.getItemName());
    title.setFont(new Font("Segoe UI", Font.BOLD, 18));

    JLabel brand = new JLabel("No Brand");
    brand.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    brand.setForeground(new Color(150, 150, 150));

    JLabel stock = new JLabel("4 item(s) left");
    stock.setFont(new Font("Segoe UI", Font.PLAIN, 11));
    stock.setForeground(new Color(150, 150, 150));

    info.add(Box.createVerticalStrut(6));
    info.add(title);
    info.add(Box.createVerticalStrut(18));
    info.add(brand);
    info.add(Box.createVerticalStrut(6));
    info.add(stock);

    gc.gridx = 2;
    gc.weightx = 1; // ✅ this column expands, prevents overflow
    gc.insets = new Insets(10, 10, 10, 10);
    row.add(info, gc);

    // ===== 4) Price + qty column =====
    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 52));
    right.setBackground(Color.WHITE);

    JLabel price = new JLabel("Rs." + (int) item.getPrice());
    price.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    right.add(price);

    boolean isBook = "BOOK".equalsIgnoreCase(item.getItemType());

    JLabel qtyLabel = new JLabel(String.valueOf(line.qty));
    qtyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    qtyLabel.setPreferredSize(new Dimension(20, 20));
    qtyLabel.setHorizontalAlignment(SwingConstants.CENTER);

    if (isBook) {
       
        JButton minus = smallQtyButton("-");
        JButton plus = smallQtyButton("+");

        minus.addActionListener(e -> {
            if (line.qty <= 1) return;
            line.qty--;
            qtyLabel.setText(String.valueOf(line.qty));
            cartController.updateCartQuantity(item.getCartId(), line.qty);
            updateOrderSummary();
        });

        plus.addActionListener(e -> {
            line.qty++;
            qtyLabel.setText(String.valueOf(line.qty));
            cartController.updateCartQuantity(item.getCartId(), line.qty);
            updateOrderSummary();
        });

        right.add(minus);
        right.add(qtyLabel);
        right.add(plus);
    } else {
        // MOVIE fixed qty
        line.qty = 1;
        qtyLabel.setText("1");
        right.add(qtyLabel);
    }

    gc.gridx = 3;
    gc.weightx = 0;
    gc.insets = new Insets(0, 0, 0, 18);
    row.add(right, gc);

    return row;
}

private JButton smallQtyButton(String txt) {
    JButton b = new JButton(txt);
    b.setPreferredSize(new Dimension(34, 24));
    b.setFocusPainted(false);
    b.setBackground(new Color(230, 230, 230));
    b.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));
    b.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return b;
}
private JPanel buildOrderSummaryPanel() {

    JPanel outer = new JPanel(new BorderLayout());
    outer.setBackground(new Color(232, 241, 253));
    outer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    JPanel card = new JPanel(new GridBagLayout());
    card.setBackground(new Color(224, 238, 251));
    card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(210, 210, 210), 1, true),
            BorderFactory.createEmptyBorder(28, 28, 28, 28)
    ));

    GridBagConstraints gc = new GridBagConstraints();
    gc.gridx = 0;
    gc.gridy = 0;
    gc.gridwidth = 2;
    gc.anchor = GridBagConstraints.WEST;
    gc.fill = GridBagConstraints.HORIZONTAL;
    gc.weightx = 1;

    // Title
    jLabel11.setText("Order Summary");
    jLabel11.setFont(new Font("Segoe UI", Font.BOLD, 14));
    card.add(jLabel11, gc);

    // helper to add rows
    java.util.function.BiConsumer<JLabel, JLabel> addRow = (left, right) -> {
        left.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        right.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        GridBagConstraints l = new GridBagConstraints();
        l.gridx = 0;
        l.gridy = ++gc.gridy;
        l.anchor = GridBagConstraints.WEST;
        l.insets = new Insets(18, 0, 0, 0);

        GridBagConstraints r = new GridBagConstraints();
        r.gridx = 1;
        r.gridy = gc.gridy;
        r.anchor = GridBagConstraints.EAST;
        r.insets = new Insets(18, 0, 0, 0);

        card.add(left, l);
        card.add(right, r);
    };

    addRow.accept(jLabel17, jLabel19);
    addRow.accept(jLabel18, jLabel20);

    // Voucher row
    JPanel voucherRow = new JPanel(new BorderLayout(12, 0));
    voucherRow.setOpaque(false);

    jTextField1.setPreferredSize(new Dimension(220, 30));
    jTextField1.setBackground(new Color(245, 245, 245));
    jTextField1.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210)));

    jButton1.setText("APPLY");
    jButton1.setPreferredSize(new Dimension(95, 30));
    jButton1.setBackground(new Color(13, 106, 210));
    jButton1.setForeground(Color.WHITE);
    jButton1.setFocusPainted(false);

    voucherRow.add(jTextField1, BorderLayout.CENTER);
    voucherRow.add(jButton1, BorderLayout.EAST);

    GridBagConstraints voucherGC = new GridBagConstraints();
    voucherGC.gridx = 0;
    voucherGC.gridy = ++gc.gridy;
    voucherGC.gridwidth = 2;
    voucherGC.fill = GridBagConstraints.HORIZONTAL;
    voucherGC.insets = new Insets(26, 0, 0, 0);
    card.add(voucherRow, voucherGC);

    // Total row
    jLabel21.setText("Total");
    jLabel21.setFont(new Font("Segoe UI", Font.BOLD, 12));
    jLabel22.setFont(new Font("Segoe UI", Font.BOLD, 12));

    GridBagConstraints t1 = new GridBagConstraints();
    t1.gridx = 0;
    t1.gridy = ++gc.gridy;
    t1.anchor = GridBagConstraints.WEST;
    t1.insets = new Insets(36, 0, 0, 0);

    GridBagConstraints t2 = new GridBagConstraints();
    t2.gridx = 1;
    t2.gridy = gc.gridy;
    t2.anchor = GridBagConstraints.EAST;
    t2.insets = new Insets(36, 0, 0, 0);

    card.add(jLabel21, t1);
    card.add(jLabel22, t2);

    // Checkout "button"
    jTextField2.setEditable(false);
    jTextField2.setHorizontalAlignment(JTextField.CENTER);
    jTextField2.setBackground(new Color(13, 106, 210));
    jTextField2.setForeground(Color.WHITE);
    jTextField2.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    jTextField2.setCursor(new Cursor(Cursor.HAND_CURSOR));

    GridBagConstraints checkoutGC = new GridBagConstraints();
    checkoutGC.gridx = 0;
    checkoutGC.gridy = ++gc.gridy;
    checkoutGC.gridwidth = 2;
    checkoutGC.fill = GridBagConstraints.HORIZONTAL;
    checkoutGC.insets = new Insets(12, 0, 0, 0);
    card.add(jTextField2, checkoutGC);

    outer.add(card, BorderLayout.NORTH);
    return outer;
}



private void updateOrderSummary() {
    int selectedCount = 0;
    double subtotal = 0;

    for (var entry : cbToLine.entrySet()) {
        JCheckBox cb = entry.getKey();
        CartLine line = entry.getValue();

        if (cb.isSelected()) {
            selectedCount++;
            subtotal += (line.item.getPrice() * line.qty);
        }
    }

    jLabel17.setText("Subtotal (" + selectedCount + " items )");
    jLabel19.setText("Rs." + (int) subtotal);

      
    jLabel22.setText("Rs=" + (int) subtotal);
    jTextField2.setText("PROCEED TO CHECKOUT(" + selectedCount + ")");

    // keep Select All sync
    if (!bulkChanging) {
        bulkChanging = true;
        chkSelectALL.setSelected(selectedCount > 0 && selectedCount == cbToLine.size());
        bulkChanging = false;
    }

    // disable checkout if 0 selected
    jTextField2.setEnabled(selectedCount > 0);
}


private void deleteSelectedItems() {
    if (loggedInUserId == -1) return;

    java.util.List<Integer> ids = new java.util.ArrayList<>();
    for (var entry : cbToLine.entrySet()) {
        if (entry.getKey().isSelected()) {
            ids.add(entry.getValue().item.getCartId());
        }
    }

    if (ids.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please select at least 1 item to delete.");
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete " + ids.size() + " selected item(s)?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    cartController.removeSelectedItems(ids);
    loadCartItems();
}

    private void deleteAllItems() {
    if (loggedInUserId == -1) return;

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "Delete ALL items from cart?",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION
    );
    if (confirm != JOptionPane.YES_OPTION) return;

    // Best way (recommended): create cartController.clearCart(userId)
    // cartController.clearCart(loggedInUserId);

    // If you don't have clearCart(), do this:
    java.util.List<CartItem> items = cartController.getCartItems(loggedInUserId);
    for (CartItem it : items) {
        cartController.removeItemFromCart(it.getCartId());
    }

    loadCartItems();
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
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
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
