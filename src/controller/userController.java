/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import Model.User_model;
import java.util.List;
import userdata.userDao;
import view.users;

/**
 *
 * @author zenni
 */
public class userController {

    public userController(users aThis) {
    }
    public void getAllUsers(users viewuser){
        userDao user = new userDao();
        List<User_model> u=user.getAllUsers();
        //todo (render users in userview)
    }
}
