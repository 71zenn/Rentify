/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package userdata;

/**
 *
 * @author Dell
 */


import model.User_model;

public class UserSession {
    private static User_model currentUser;

    public static void setCurrentUser(User_model user) {
        currentUser = user;
    }

    public static User_model getCurrentUser() {
        return currentUser;
    }

    public static int getUserId() {
        return currentUser == null ? -1 : currentUser.getUserID();
    }

    public static void clear() {
        currentUser = null;
    }
}
