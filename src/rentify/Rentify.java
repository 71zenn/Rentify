/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rentify;
import controller.userController;
import database.*;

import view.*;

/**
 *
 * @author zenni
 */
public class Rentify {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        registration registration_obj = new registration();
        userController controller = new userController();
        controller.open();
    }
}     
