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
import userdata.RentalDao;

public class RentalHistoryController {

    private RentalDao rentalDAO;

    public RentalHistoryController() {
        rentalDAO = new RentalDao();
    }

    public List<Rental> getRentalHistory(int userId) {
        return rentalDAO.getRentalHistory(userId);
    }
}

