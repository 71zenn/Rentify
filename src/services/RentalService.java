/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

/**
 *
 * @author Dell
 */
import java.util.List;
import userdata.RentalDao;
import model.Rental;
public class RentalService {

    private RentalDao rentalDAO;

    public RentalService() {
        rentalDAO = new RentalDao();
    }

    public List<Rental> getRentalHistory(int userId) {
        return rentalDAO.getRentalHistory(userId);
    }
}

