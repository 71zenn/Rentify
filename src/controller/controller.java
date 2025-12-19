/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import userdata.userDao;

import Model.User_model;

public class controller {
    private final userDao userdao = new userDao();

    public boolean loginUser(String username, String password) {

        // We don't need email for login right now, so we pass empty string
        User_model user = new User_model(username, "", password);

        // Call DAO to check in database
        return userdao.login(user);
    }

    public boolean Login(String username, String password) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
