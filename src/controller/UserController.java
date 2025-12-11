/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import userdata.UserDao;

import Model.User_model;

public class UserController {
    private final UserDao userdao = new UserDao();

    public boolean loginUser(String username, String password) {

        // We don't need email for login right now, so we pass empty string
        User_model user = new User_model(username, "", password);

        // Call DAO to check in database
        return userdao.login(user);
    }

}
