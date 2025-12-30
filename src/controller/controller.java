package controller;

import model.User_model;
import userdata.UserSession;
import userdata.userDao;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import view.Purchasehistory; // ✅ open this after login

public class controller {

    private final userDao userdao = new userDao();

    public void loginAndOpenPurchaseHistory(String username, String password, JFrame loginFrame) {

        if (username == null) username = "";
        if (password == null) password = "";

        username = username.trim();
        password = password.trim();

        if (username.isEmpty() || password.isEmpty()
                || "Username".equalsIgnoreCase(username)
                || "Password".equalsIgnoreCase(password)) {
            JOptionPane.showMessageDialog(loginFrame, "Please enter username and password!");
            return;
        }

        User_model logged = userdao.loginAndGetUser(new User_model(username, "", password));

        System.out.println("Login result = " + (logged != null));

        if (logged != null) {
            // ✅ STORE SESSION HERE
            UserSession.setCurrentUser(logged);

            JOptionPane.showMessageDialog(loginFrame,
                    "Login Successful! Welcome " + logged.getUsername());

            // ✅ OPEN PURCHASE HISTORY
            Purchasehistory p = new Purchasehistory();
            p.setLocationRelativeTo(null);
            p.setVisible(true);

            // ✅ CLOSE LOGIN
            if (loginFrame != null) loginFrame.dispose();

        } else {
            JOptionPane.showMessageDialog(loginFrame, "Invalid username or password!");
        }
    }
}
