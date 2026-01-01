/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import static java.util.Collections.list;
import java.util.List;

import userdata.OrderDao;
import model.Order;

/**
 *
 * @author Dell
 */
public class Purchasehistory extends javax.swing.JFrame {
    private final OrderDao orderDAO = new OrderDao();

// TODO: replace this with YOUR login/session user id provider
private int getLoggedInUserId() {
    // Example only:
    // return Session.getCurrentUser().getUserId();
  ;
    return userdata.UserSession.getUserId(); // change to your real session class
}

private final SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");

    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Purchasehistory.class.getName());

    /**
     * Creates new form 
     */
    public Purchasehistory() {
    initComponents();
    

    // ✅ Make jPanel2 dynamic list
    // ✅ bring the scroll pane above the background panel
    getContentPane().setComponentZOrder(Backgroundcolorpanel, getContentPane().getComponentCount() - 1);
    getContentPane().setComponentZOrder(scrlPaneOne, 0); // 0 = top/front

    // ✅ Make jPanel2 dynamic list
    jPanel2.removeAll();
    
    jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.Y_AXIS));
    jPanel2.setBackground(Color.WHITE);
     pack();                 // ✅ important
    setLocationRelativeTo(null);
    setVisible(true);
    loadPurchaseHistory();
}
    
    
    
    private void loadPurchaseHistory() {
    int userId = getLoggedInUserId();

    if (userId == -1) {
        JOptionPane.showMessageDialog(this, "Please login again.");
        // new Login().setVisible(true); // if you have login view
        dispose();
        return;
    }
   


    List<Order> list = orderDAO.getPurchaseHistory(userId);
     System.out.println("UserId = " + userId);
System.out.println("Orders size = " + (list == null ? "null" : list.size()));

    jPanel2.removeAll();

    if (list == null || list.isEmpty()) {
        JLabel empty = new JLabel("No purchases found.");
        empty.setBorder(new EmptyBorder(20, 20, 20, 20));
        jPanel2.add(empty);
    } else {
        for (Order order : list) {
            jPanel2.add(createPurchaseCard(order));
            jPanel2.add(Box.createVerticalStrut(10));
            jPanel2.add(new JSeparator());
            jPanel2.add(Box.createVerticalStrut(10));
        }
    }

    jPanel2.revalidate();
    jPanel2.repaint();
}

private JPanel createPurchaseCard(Order order) {
    JPanel card = new JPanel(new BorderLayout(15, 10));
    card.setBackground(Color.WHITE);
    card.setBorder(new EmptyBorder(10, 10, 10, 10));
    card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

    // LEFT image (placeholder)
    JLabel img = new JLabel();
    img.setPreferredSize(new Dimension(115, 115));

    // Use a default placeholder icon (create this file in /icons/)
    ImageIcon icon = null;
    try {
        icon = new ImageIcon(getClass().getResource("/icons/placeholder.png"));
    } catch (Exception e) {
        // ignore if missing
    }
    img.setIcon(icon);

    // CENTER info
    JPanel info = new JPanel();
    info.setBackground(Color.WHITE);
    info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

    JLabel name = new JLabel(order.getItemName());
    name.setFont(new Font("Segoe UI", Font.BOLD, 13));

    String dateText = (order.getPurchaseDate() != null)
            ? sdf.format(order.getPurchaseDate())
            : "N/A";

    JLabel date = new JLabel("Purchased on: " + dateText);
    date.setForeground(new Color(102, 102, 102));

    JLabel typePrice = new JLabel("Type: " + order.getItemType() + "    Price: " + order.getPrice());
    typePrice.setForeground(new Color(102, 102, 102));

    info.add(name);
    info.add(Box.createVerticalStrut(6));
    info.add(date);
    info.add(Box.createVerticalStrut(4));
    info.add(typePrice);

    // RIGHT download button (optional)
    JButton download = new JButton("Download");
    download.setBackground(new Color(13, 106, 210));
    download.setForeground(Color.WHITE);
    download.setFocusPainted(false);
    download.setPreferredSize(new Dimension(110, 30));

    download.addActionListener(e -> {
        JOptionPane.showMessageDialog(this,
                "Download not implemented yet for: " + order.getItemName());
    });

    JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    right.setBackground(Color.WHITE);
    right.add(download);

    card.add(img, BorderLayout.WEST);
    card.add(info, BorderLayout.CENTER);
    card.add(right, BorderLayout.EAST);

    return card;
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        Rentifylogo = new javax.swing.JLabel();
        Librarybutton = new javax.swing.JButton();
        Booksbutton = new javax.swing.JButton();
        Moviesbutton = new javax.swing.JButton();
        Newestbutton = new javax.swing.JButton();
        Supportbutton = new javax.swing.JButton();
        Search_Icon = new javax.swing.JLabel();
        CartIcon = new javax.swing.JLabel();
        Mainsearchbar = new javax.swing.JTextField();
        Profileicon = new javax.swing.JLabel();
        Pageone = new javax.swing.JButton();
        Pagetwo = new javax.swing.JButton();
        Pagethree = new javax.swing.JButton();
        Pagefour = new javax.swing.JButton();
        scrlPaneOne = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        HarryBox = new javax.swing.JLabel();
        HPtxt = new javax.swing.JLabel();
        TheLibraryBox = new javax.swing.JLabel();
        HamletBox = new javax.swing.JLabel();
        HPDate = new javax.swing.JLabel();
        HPtype = new javax.swing.JLabel();
        Librarytxt = new javax.swing.JLabel();
        LibraryDate = new javax.swing.JLabel();
        LibraryType = new javax.swing.JLabel();
        Hamlettxt = new javax.swing.JLabel();
        HamletDate = new javax.swing.JLabel();
        HamletType = new javax.swing.JLabel();
        DownloadHP = new javax.swing.JButton();
        DownloadLibrary = new javax.swing.JButton();
        DownloadHamlet = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        Backgroundcolorpanel = new javax.swing.JPanel();
        Viewpurchasebutton = new javax.swing.JButton();
        Allbutton = new javax.swing.JButton();
        NewButton2 = new javax.swing.JButton();
        Background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().setLayout(null);

        Rentifylogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Rentifylogo.png"))); // NOI18N
        getContentPane().add(Rentifylogo);
        Rentifylogo.setBounds(20, 10, 250, 120);

        Librarybutton.setBackground(new java.awt.Color(232, 241, 253));
        Librarybutton.setText("Library");
        Librarybutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Librarybutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LibrarybuttonActionPerformed(evt);
            }
        });
        getContentPane().add(Librarybutton);
        Librarybutton.setBounds(310, 60, 120, 30);

        Booksbutton.setBackground(new java.awt.Color(232, 241, 253));
        Booksbutton.setText("Books");
        Booksbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Booksbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookButtonHandler(evt);
            }
        });
        getContentPane().add(Booksbutton);
        Booksbutton.setBounds(450, 60, 120, 30);

        Moviesbutton.setBackground(new java.awt.Color(232, 241, 253));
        Moviesbutton.setText("Movies");
        Moviesbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Moviesbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoviesbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(Moviesbutton);
        Moviesbutton.setBounds(610, 60, 120, 30);

        Newestbutton.setBackground(new java.awt.Color(232, 241, 253));
        Newestbutton.setText("Newest");
        Newestbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Newestbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewestbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(Newestbutton);
        Newestbutton.setBounds(770, 60, 120, 30);

        Supportbutton.setBackground(new java.awt.Color(232, 241, 253));
        Supportbutton.setText("Support");
        Supportbutton.setPreferredSize(new java.awt.Dimension(146, 36));
        Supportbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SupportbuttonActionPerformed(evt);
            }
        });
        getContentPane().add(Supportbutton);
        Supportbutton.setBounds(930, 60, 120, 30);

        Search_Icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search_Icon.png"))); // NOI18N
        getContentPane().add(Search_Icon);
        Search_Icon.setBounds(1110, 150, 30, 30);

        CartIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cart_icon.png"))); // NOI18N
        CartIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HandleCartOpen(evt);
            }
        });
        getContentPane().add(CartIcon);
        CartIcon.setBounds(1150, 150, 30, 25);

        Mainsearchbar.setBackground(new java.awt.Color(229, 231, 235));
        Mainsearchbar.setForeground(new java.awt.Color(107, 114, 128));
        Mainsearchbar.setText("Search");
        Mainsearchbar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
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
        getContentPane().add(Mainsearchbar);
        Mainsearchbar.setBounds(820, 150, 320, 30);

        Profileicon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/profile icon.png"))); // NOI18N
        getContentPane().add(Profileicon);
        Profileicon.setBounds(1140, 40, 60, 60);

        Pageone.setText("1");
        Pageone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PageoneActionPerformed(evt);
            }
        });
        getContentPane().add(Pageone);
        Pageone.setBounds(570, 680, 20, 20);

        Pagetwo.setText("2");
        Pagetwo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PagetwoActionPerformed(evt);
            }
        });
        getContentPane().add(Pagetwo);
        Pagetwo.setBounds(600, 680, 20, 20);

        Pagethree.setText("3");
        Pagethree.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PagethreeActionPerformed(evt);
            }
        });
        getContentPane().add(Pagethree);
        Pagethree.setBounds(630, 680, 20, 20);

        Pagefour.setText("4");
        Pagefour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PagefourActionPerformed(evt);
            }
        });
        getContentPane().add(Pagefour);
        Pagefour.setBounds(660, 680, 20, 20);

        scrlPaneOne.setBackground(new java.awt.Color(255, 255, 255));
        scrlPaneOne.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        HarryBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Harry.png"))); // NOI18N

        HPtxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HPtxt.setText("Harry Potter ");

        TheLibraryBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/The secret library.png"))); // NOI18N

        HamletBox.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Hamlet.png"))); // NOI18N

        HPDate.setForeground(new java.awt.Color(102, 102, 102));
        HPDate.setText("Purchased on :11 Nov, 2025");

        HPtype.setForeground(new java.awt.Color(102, 102, 102));
        HPtype.setText("Type : Digital            Order ID : 27483323");

        Librarytxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Librarytxt.setText("The Secret Library");

        LibraryDate.setForeground(new java.awt.Color(102, 102, 102));
        LibraryDate.setText("Purchased on : 11 Nov, 2025");

        LibraryType.setForeground(new java.awt.Color(102, 102, 102));
        LibraryType.setText("Type : Paperback   Order ID : 38766866");

        Hamlettxt.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Hamlettxt.setText("Hamlet");

        HamletDate.setForeground(new java.awt.Color(102, 102, 102));
        HamletDate.setText("Purchased on : 20 Nov, 2025");

        HamletType.setForeground(new java.awt.Color(102, 102, 102));
        HamletType.setText("Type : ebook          Order ID : 44778389");

        DownloadHP.setBackground(new java.awt.Color(13, 106, 210));
        DownloadHP.setForeground(new java.awt.Color(255, 255, 255));
        DownloadHP.setText("Download");

        DownloadLibrary.setBackground(new java.awt.Color(13, 106, 210));
        DownloadLibrary.setForeground(new java.awt.Color(255, 255, 255));
        DownloadLibrary.setText("Download");

        DownloadHamlet.setBackground(new java.awt.Color(13, 106, 210));
        DownloadHamlet.setForeground(new java.awt.Color(255, 255, 255));
        DownloadHamlet.setText("Download");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(HarryBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TheLibraryBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HamletBox, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HPtxt, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Librarytxt, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(HamletType, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(HPtype, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 592, Short.MAX_VALUE)
                                .addComponent(DownloadHP, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Hamlettxt, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(HamletDate, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DownloadHamlet, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(LibraryDate, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(LibraryType, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(DownloadLibrary, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(HPDate, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(71, 71, 71))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(jSeparator4))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(HarryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(HPtxt)
                        .addGap(13, 13, 13)
                        .addComponent(HPDate)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(DownloadHP)
                            .addComponent(HPtype))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(Librarytxt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(LibraryDate)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LibraryType))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(DownloadLibrary)
                                .addGap(15, 15, 15))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TheLibraryBox, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(HamletBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(Hamlettxt)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(HamletDate))
                            .addComponent(DownloadHamlet))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(HamletType)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        scrlPaneOne.setViewportView(jPanel2);

        getContentPane().add(scrlPaneOne);
        scrlPaneOne.setBounds(80, 260, 1130, 410);

        Backgroundcolorpanel.setBackground(new java.awt.Color(255, 255, 255));
        Backgroundcolorpanel.setMinimumSize(new java.awt.Dimension(1280, 720));

        Viewpurchasebutton.setBackground(new java.awt.Color(249, 250, 251));
        Viewpurchasebutton.setForeground(new java.awt.Color(102, 102, 102));
        Viewpurchasebutton.setText("View Purchase");
        Viewpurchasebutton.setPreferredSize(new java.awt.Dimension(146, 36));

        Allbutton.setBackground(new java.awt.Color(229, 231, 235));
        Allbutton.setForeground(new java.awt.Color(107, 114, 128));
        Allbutton.setText("All");
        Allbutton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllbuttonActionPerformed(evt);
            }
        });

        NewButton2.setBackground(new java.awt.Color(229, 231, 235));
        NewButton2.setForeground(new java.awt.Color(107, 114, 128));
        NewButton2.setText("Newest");
        NewButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout BackgroundcolorpanelLayout = new javax.swing.GroupLayout(Backgroundcolorpanel);
        Backgroundcolorpanel.setLayout(BackgroundcolorpanelLayout);
        BackgroundcolorpanelLayout.setHorizontalGroup(
            BackgroundcolorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundcolorpanelLayout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(BackgroundcolorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Viewpurchasebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BackgroundcolorpanelLayout.createSequentialGroup()
                        .addComponent(Allbutton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NewButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1063, Short.MAX_VALUE))
        );
        BackgroundcolorpanelLayout.setVerticalGroup(
            BackgroundcolorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackgroundcolorpanelLayout.createSequentialGroup()
                .addGap(192, 192, 192)
                .addComponent(Viewpurchasebutton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(BackgroundcolorpanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Allbutton)
                    .addComponent(NewButton2))
                .addContainerGap(471, Short.MAX_VALUE))
        );

        getContentPane().add(Backgroundcolorpanel);
        Backgroundcolorpanel.setBounds(0, 0, 1270, 720);

        Background.setBackground(new java.awt.Color(255, 255, 255));
        getContentPane().add(Background);
        Background.setBounds(0, 0, 1280, 720);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LibrarybuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LibrarybuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_LibrarybuttonActionPerformed

    private void AllbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AllbuttonActionPerformed

    private void PageoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PageoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PageoneActionPerformed

    private void MainsearchbarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MainsearchbarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MainsearchbarActionPerformed

    private void PagefourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PagefourActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PagefourActionPerformed

    private void PagethreeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PagethreeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PagethreeActionPerformed

    private void PagetwoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PagetwoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PagetwoActionPerformed

    private void MainsearchbarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MainsearchbarFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_MainsearchbarFocusGained

    private void MainsearchbarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_MainsearchbarFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_MainsearchbarFocusLost

    private void MoviesbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoviesbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MoviesbuttonActionPerformed

    private void SupportbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SupportbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_SupportbuttonActionPerformed

    private void BookButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookButtonHandler
        // TODO add your handling code here:
    }//GEN-LAST:event_BookButtonHandler

    private void NewButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewButton2ActionPerformed

    private void NewestbuttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewestbuttonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NewestbuttonActionPerformed

    private void HandleCartOpen(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HandleCartOpen
       int userId = getLoggedInUserId();

    if (userId == -1) {
        JOptionPane.showMessageDialog(this, "Please login again.");
        return;
    }

    // Open cart view
    Cart cartPage = new Cart();
    cartPage.setVisible(true);

    // Optional: close purchase history
    this.dispose();
    }//GEN-LAST:event_HandleCartOpen
   
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
        java.awt.EventQueue.invokeLater(() -> new Purchasehistory().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Allbutton;
    private javax.swing.JLabel Background;
    private javax.swing.JPanel Backgroundcolorpanel;
    private javax.swing.JButton Booksbutton;
    private javax.swing.JLabel CartIcon;
    private javax.swing.JButton DownloadHP;
    private javax.swing.JButton DownloadHamlet;
    private javax.swing.JButton DownloadLibrary;
    private javax.swing.JLabel HPDate;
    private javax.swing.JLabel HPtxt;
    private javax.swing.JLabel HPtype;
    private javax.swing.JLabel HamletBox;
    private javax.swing.JLabel HamletDate;
    private javax.swing.JLabel HamletType;
    private javax.swing.JLabel Hamlettxt;
    private javax.swing.JLabel HarryBox;
    private javax.swing.JLabel LibraryDate;
    private javax.swing.JLabel LibraryType;
    private javax.swing.JButton Librarybutton;
    private javax.swing.JLabel Librarytxt;
    private javax.swing.JTextField Mainsearchbar;
    private javax.swing.JButton Moviesbutton;
    private javax.swing.JButton NewButton2;
    private javax.swing.JButton Newestbutton;
    private javax.swing.JButton Pagefour;
    private javax.swing.JButton Pageone;
    private javax.swing.JButton Pagethree;
    private javax.swing.JButton Pagetwo;
    private javax.swing.JLabel Profileicon;
    private javax.swing.JLabel Rentifylogo;
    private javax.swing.JLabel Search_Icon;
    private javax.swing.JButton Supportbutton;
    private javax.swing.JLabel TheLibraryBox;
    private javax.swing.JButton Viewpurchasebutton;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JScrollPane scrlPaneOne;
    // End of variables declaration//GEN-END:variables
}
