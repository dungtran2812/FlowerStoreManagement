/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Tools.InputHandler;
import Models.Flower;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * FlowerManagement class represents a set of flowers and provides various operations on them.
 *
 */
public class FlowerManagement extends HashSet<Flower> {

    Scanner sc = new Scanner(System.in);
    
    /**
     * Create a new flower and add it to the set.
     *
     * @param id The flower ID.
     */
    public void createFlower(String id) {

        String description = InputHandler.getValidDescription();
        String importDate = InputHandler.getDate("Enter Date (mm/dd/yyyy): ");
        int unitPrice = InputHandler.getPositiveValue("Enter unit price: ");
        String category = InputHandler.getValidString("Enter Category: ");
        Flower f = new Flower(id, description, importDate, unitPrice, category);
        this.add(f);

    }
    
    /**
     * Find a flower by its ID.
     *
     * @param id        The flower ID to search for.
     * @param printInfo If true, print the flower's information.
     * @return The found flower or null if not found.
     */
    public Flower findFlowerById(String id, boolean printInfo) {
        Flower flowerFinded = null;
        boolean finded = false;
        if (printInfo) {
            System.out.println("----------------------------------------------------------------------------------------------------------------");
            System.out.format("|%-12s|%-20s|%-30s|%-15s|%-30s|\n", "Flower Id", "Name", "Import Date", "Unit Price", "Category");
            System.out.println("----------------------------------------------------------------------------------------------------------------");
        }
        for (Flower flower : this) {

            if (flower.getId().equalsIgnoreCase(id)) {
                if (printInfo) {
                    System.out.format("|%-12s|%-20s|%-30s|%-15s|%-30s|\n",
                            flower.getId(), flower.getDescription(), flower.getImportDate(), flower.getUnitPrice(), flower.getCategory());
                }

                finded = true;
                flowerFinded = flower;
            }
        }
        if (!finded) {
            System.out.println("Flower doesnt exist.");
        }

        return flowerFinded;
    }
    
    /**
     * Find a flower by its name.
     *
     * @param name The name to search for.
     * @return The found flower or null if not found.
     */
    public Flower findFlowerByName(String name) {
        Flower flowerFinded = null;
        boolean finded = false;
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.format("|%-12s|%-20s|%-30s|%-15s|%-30s|\n", "Flower Id", "Name", "Import Date", "Unit Price", "Category");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Flower flower : this) {
            if (flower.getDescription().contains(name)) {
                System.out.format("|%-12s|%-20s|%-30s|%-15s|%-30s|\n",
                        flower.getId(), flower.getDescription(), flower.getImportDate(), flower.getUnitPrice(), flower.getCategory());
                finded = true;
                flowerFinded = flower;
            }
        }
        if (!finded) {
            System.out.println("Flower doesnt exist.");
        }

        return flowerFinded;
    }
    
    /**
     * Update the information of a flower.
     *
     * @param name The name of the flower to update.
     */
    public void updateFlower(String name) {
        Flower flowerFinded = findFlowerByName(name);
        while (flowerFinded == null) {
            flowerFinded = findFlowerByName(name);
        }
        System.out.println("Update info: ");

        flowerFinded.setImportDate(InputHandler.getDate("Enter Date (dd/mm/yyyy): "));
        flowerFinded.setUnitPrice(InputHandler.getPositiveValue("Enter Unit price: "));
        flowerFinded.setCategory("Enter Category: ");
        System.out.println("New info:");
        System.out.println(flowerFinded);
    }
    
    /**
     * Delete a flower from the set.
     *
     * @param fDelete The flower to delete.
     */
    public void deleteFlower(Flower fDelete) {
        if (InputHandler.yesNoConfirm("Do you want to delete this flower(y/n)?.")) {
            this.remove(fDelete);
            System.out.println("Flower has been delete.");
        }

    }

    /**
     * Save the set of flowers to a file.
     *
     * @param filePath The path of the file to save to.
     */
    public void saveToFile(String filePath) {
        if (this.isEmpty()) {
            System.out.println("Flower's set empty!");
            return;
        }
        try {
            FileOutputStream f = new FileOutputStream(filePath);
            ObjectOutputStream fo = new ObjectOutputStream(f);
            Iterator iter = this.iterator();
            while (iter.hasNext()) {
                Object obj = iter.next();
                fo.writeObject(obj);
            }
            f.close();
            fo.close();
            System.out.println("Flower's set has been saved!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Load the set of flowers from a file.
     *
     * @param filePath The path of the file to load from.
     * @throws ClassNotFoundException If the Flower class is not found.
     */
    public void loadFromFile(String filePath) throws ClassNotFoundException {
        if (!this.isEmpty()) {
            this.clear();
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Flower's set is empty!");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Flower flower;
            boolean quit = false;
            while (!quit) {
                try {
                    flower = (Flower) objectInputStream.readObject();
                    this.add(flower);
                } catch (EOFException e) {
                    quit = true;
                    break; // Reached the end of the file, break out of the loop(End of File exception)
                }
            }
            fileInputStream.close();
            objectInputStream.close();
            System.out.println("Flower's set has been loaded!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
