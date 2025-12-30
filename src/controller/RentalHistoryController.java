/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Dell
 */

import java.util.List;
import model.Rental;
import userdata.RentalDAO;

public class RentalHistoryController {

    private RentalDAO rentalDAO;

    public RentalHistoryController() {
        rentalDAO = new RentalDAO();
    }

    public List<Rental> getRentalHistory(int userId) {
        return rentalDAO.getRentalHistory(userId);
    }
}

