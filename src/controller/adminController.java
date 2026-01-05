/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.User_model;
import java.util.List;
import userdata.adminDao;

public class adminController {

    private adminDao dao;
    
    public adminController() {
        dao = new adminDao();
    }
    public boolean loginUser(String username, String password) {
        return dao.login(username, password);
    }
    public List<User_model> getAllUsers() {
        return dao.getAllUsers();
    }

    public User_model getUserById(int userId) {
        return dao.getUserById(userId);
    }

    public void updateUser(User_model user) {
        dao.updateUser(user);
    }

    public void deleteUser(int userId) {
        dao.deleteUser(userId);
    }
}
