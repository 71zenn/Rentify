/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author ASUS
 */
public class Product {
    public int id;
    public String name;
    public String type;
    public String imagepath;
    public String category;
    
    
    public Product(){}
    
    public Product(String name, String type, String imagepath, String category){
        this.name = name;
        this.type = type;
        this.imagepath = imagepath;
        this.category = category;
    }
    
}

