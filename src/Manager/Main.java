package Manager;

import Business.FlowerManagement;
import Business.FlowerStore;
import Models.Flower;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * This is the main class for the flower store management system.
 * It provides a menu-driven interface for interacting with the FlowerStore.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, ClassNotFoundException {
        FlowerStore fs = new FlowerStore();
        int choice;
        boolean quit = false;
        do {
            System.out.println("=======MENU======");
            System.out.println("1 - Add a flower");
            System.out.println("2 - Find a flower");
            System.out.println("3 - Update a flower");
            System.out.println("4 - Delete a flower");
            System.out.println("5 - Add an order");
            System.out.println("6 - Display orders");
            System.out.println("7 - Sort orders");
            System.out.println("8 - Save data");
            System.out.println("9 - Load data");
            System.out.println("10 - Quit");
            System.out.println("=================");
            System.out.print("Enter Option: ");

            Scanner sc = new Scanner(System.in);
            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid option.");
                sc.nextLine(); // Clear the invalid input from the scanner
                continue;
            }

            switch (choice) {
                case 1:
                    fs.addFlower();
                    break;
                case 2:
                    fs.findFlower();
                    break;
                case 3:
                    fs.updateFlower();
                    break;
                case 4:
                    fs.deleteFlower();
                    break;
                case 5:
                    fs.addOrder();
                    break;
                case 6:
                    fs.displayOrders();
                    break;
                case 7:
                    fs.sortOrders();
                    break;
                case 8:
                    fs.saveData();
                    break;
                case 9:
                    fs.loadData();
                    break;
                case 10:
                    quit = true;
                    fs.quit();
                    break;
                case 11:
                    fs.displayAllOrderDetailById();
                    break;
                default:
                    System.out.println("Invalid Option. Please enter a valid option.");
            }
        } while (!quit);
    }

}
