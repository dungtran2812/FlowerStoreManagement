/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business;

import Models.Flower;
import Models.Order;
import Models.OrderDetail;
import Tools.InputHandler;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * FlowerStore class represents a flower store management system.
 */
public class FlowerStore {

    boolean hasChange = false;
    Scanner sc = new Scanner(System.in);
    FlowerManagement flowerManagement = new FlowerManagement();
    OrderManagement orderManagement = new OrderManagement();
    
    /**
     * addFlower method allows the user to add a new flower to the flower management system.
     * Prompts the user to enter the flower ID and checks for uniqueness.
     * Creates a new Flower object with the entered ID and adds it to the FlowerManagement.
     * Asks the user if they want to add more flowers.
     */
    public void addFlower() {
        String id = null;
        do {
            boolean uniqueId = false;

            while (!uniqueId) {
                id = InputHandler.getFormattedString("Enter id(Fxxx): ", "^F\\d{3}$");
                if (!flowerManagement.isEmpty()) {

                    for (Flower f : flowerManagement) {

                        if (f.getId().equalsIgnoreCase(id)) {
                            uniqueId = false;
                            System.out.println("Id already exist. ");
                            break;
                        } else {
                            uniqueId = true;
                        }
                    }
                } else {
                    uniqueId = true;
                }
            }

            flowerManagement.createFlower(id);

        } while (InputHandler.yesNoConfirm("Do you want to add more flower(y/n): "));
        hasChange = true;
    }
    
    /**
     * findFlower method allows the user to find a flower in the flower management system.
     * Prompts the user to choose a search criteria: ID or Name.
     * Based on the chosen criteria, prompts the user to enter the corresponding information.
     * Searches for the flower using the FlowerManagement and displays the result.
     */
    public void findFlower() {
        System.out.println("Find flower by: ");
        System.out.println("1. ID");
        System.out.println("2. Name");
        System.out.println("Enter choice: ");
        String choice = sc.nextLine();

        if (choice.equals("1")) {
            
            flowerManagement.findFlowerById(InputHandler.getValidString("Enter Id: "), true);
        } else if (choice.equals("2")) {
            
            flowerManagement.findFlowerByName(InputHandler.getValidString("Enter Name: "));
        } else {
            System.out.println("Invalid Option. ");
        }
    }
    
    /**
     * updateFlower method allows the user to update an existing flower in the flower management system.
     * Prompts the user to enter the flower ID to be updated.
     * Checks if the flower exists in the FlowerManagement.
     * If the flower exists, updates the flower in the FlowerManagement.
     * Asks the user if they want to continue updating more flowers.
     */
    public void updateFlower() {
        String name = null;
        boolean finded = false;
        do {
            name = InputHandler.getValidDescription();
            if (!flowerManagement.isEmpty()) {

                for (Flower f : flowerManagement) {

                    if (f.getId().equalsIgnoreCase(name)) {
                        finded = true;
                        flowerManagement.updateFlower(name);
                        break;
                    }
                }
            }
            if (!finded) {
                System.out.println("Id doesnt exist.");
            }
        } while (InputHandler.yesNoConfirm("Continue or exit (Y/N): "));
        hasChange = true;
    }
    
    /**
     * deleteFlower method allows the user to delete a flower from the flower management system.
     * Prompts the user to enter the flower ID to be deleted.
     * Checks if the flower has any associated orders, and if so, prevents deletion.
     * Deletes the flower from the FlowerManagement if it exists.
     */
    public void deleteFlower() {
        System.out.println("Enter flower id for delete: ");
        String id = sc.nextLine();
        Flower fDelete = flowerManagement.findFlowerById(id, true);
        //check have order

        for (Order order : orderManagement) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                if (orderDetail.getFlowerId().equals(id)) {
                    System.out.println("Flower cant delete because it has order.");
                    return;

                }
            }

        }
        if (fDelete != null) {
            flowerManagement.deleteFlower(fDelete);

        }

        hasChange = true;

        //add feature can not delete if have order
    }
    
    /**
     * addOrder method allows the user to add a new order to the order management system.
     * Prompts the user to enter the order ID and creates a new Order object.
     * Uses the FlowerManagement to retrieve available flowers for the order.
     * Adds the Order object to the OrderManagement.
     */
    public void addOrder() {
        String orderId = null;
        boolean quit = false;
        if (flowerManagement.isEmpty()) {
            System.out.println("No available flower for order please add flower.");
            return;
        }
        
            if (!orderManagement.isEmpty()) {
            while (!quit) {
                orderId = InputHandler.getValidString("Enter Order Id: ");
                for (Order om : orderManagement) {
                    if (!om.getOrderId().equals(orderId)) {
                        quit = true;
                    } else {
                        quit = false;
                        break;
                    }
                }
            }
        } else {
            orderId = InputHandler.getValidString("Enter Order Id: ");
        }

        orderManagement.createOrder(orderId, flowerManagement);
        
        
        

        hasChange = true;
    }
    
    /**
     * displayOrders method allows the user to display orders within a specified date range.
     * Prompts the user to enter the start and end dates for the range.
     * Displays the orders from the OrderManagement that fall within the specified range.
     *
     * @throws ParseException if there is an error in parsing the entered dates
     */
    public void displayOrders() throws ParseException {
        String startDate;
        String endDate;

        do {
            startDate = InputHandler.getDate("Enter Start Date(mm/dd/yyyy): ");
            endDate = InputHandler.getDate("Enter End Date(mm/dd/yyyy): ");
            if (!InputHandler.checkBeforeAfter(startDate, endDate)) {
                System.out.println("End date must be after start date.");
            }
        } while (!InputHandler.checkBeforeAfter(startDate, endDate));
        orderManagement.displayOrders(startDate, endDate);

    }
    
    /**
     * sortOrders method allows the user to sort orders based on a specified field.
     * Prompts the user to choose a field for sorting: Order ID, Order Date, Customer Name, or Order Total.
     * Prompts the user to choose the sorting order: ASC (ascending) or DESC (descending).
     * Sorts the orders in the OrderManagement based on the chosen field and order.
     * Displays the sorted orders with their corresponding details.
     */
    public void sortOrders() {
        String sortField;
        int flowerCountTotal = 0;
        int allOrderTotal = 0;
        System.out.println("1. Order id");
        System.out.println("2. Order date");
        System.out.println("3. Customer name");
        System.out.println("4. Order total");
        System.out.println("Enter choice: ");
        sortField = sc.nextLine();
        List<Order> fieldSortedSet = new ArrayList<>(orderManagement.sortByField(orderManagement, sortField));

        String sortOrder;
        System.out.println("1. ASC");
        System.out.println("2. DESC");
        System.out.println("Enter choice: ");
        sortOrder = sc.nextLine();
        List<Order> orderSortedSet = new ArrayList<>(orderManagement.sortByOrder(fieldSortedSet, sortOrder));
        int count = 0;
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.format("|%-5s|%-12s|%-14s|%-30s|%-15s|%-30s|\n", "No.", "Order Id", "Order Date", "Customer", "Flower count", "Order Total");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Order o : orderSortedSet) {
            flowerCountTotal += o.getFlowerCount();
            allOrderTotal += o.getOrderTotal();
            count++;
            System.out.format("|%-5d|%-12s|%-14s|%-30s|%-15s|%-30s|\n",
                    count, o.getOrderId(), o.getOrderDate(), o.getCustomerName(), o.getFlowerCount(), o.getOrderTotal());

        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-5s|%-12s|%-14s|%-30s|%-15s|%-30s|\n", "", "Total", "", "", flowerCountTotal, allOrderTotal);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        hasChange = true;
    }
    
    /**
     * saveData method saves the flower and order data to files.
     * The flower data is saved to "src\\Data\\Flower.dat".
     * The order data is saved to "src\\Data\\Order.dat".
     */
    public void saveData() {
        flowerManagement.saveToFile("src\\Data\\Flower.dat");
        orderManagement.saveToFile("src\\Data\\Order.dat");
    }
    
    /**
     * loadData method loads the flower and order data from files.
     * The flower data is loaded from "src\\Data\\Flower.dat".
     * The order data is loaded from "src\\Data\\Order.dat".
     */
    public void loadData() throws ClassNotFoundException {
        flowerManagement.loadFromFile("src\\Data\\Flower.dat");
        orderManagement.loadFromFile("src\\Data\\Order.dat");
    }
    
    /**
     * exit method saves the data and exits the program.
     */
    public void quit() {
        if (InputHandler.yesNoConfirm("Do you want to exit program ? (Y/N)")) {
            if (hasChange) {
                if (InputHandler.yesNoConfirm("Data has changed, Do you want to save data? (Y/N)")) {
                    this.saveData();
                }

            }

            System.exit(0);
        }
    }
    public void displayAllOrderDetailById() {
        System.out.print("Enter id:");
        String id;
        id = sc.nextLine();
        orderManagement.displayAllOrderDetailById(id);
    }
}
