/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tools;

import Business.FlowerManagement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 * This class provides utility methods for handling user input in the flower store management system.
 */
public class InputHandler {

    static Scanner sc = new Scanner(System.in);
    
    /**
     * Prompts the user for a yes or no confirmation.
     * 
     * @param msg The message to display to the user.
     * @return true if the user confirms with 'y', false if the user confirms with 'n'.
     */
    public static boolean yesNoConfirm(String msg) {
        String choice;
        boolean confirm = false;
        boolean quit = false;
        do {
            System.out.print(msg);
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                confirm = true;
                quit = true;
            } else if (choice.equalsIgnoreCase("n")) {
                confirm = false;
                quit = true;
            } else {
                System.out.println("Invalid Choice");
            }
        } while (!quit);
        return confirm;
    }
    
    /**
     * Prompts the user for a valid string input that is not empty.
     * 
     * @param msg The message to display to the user.
     * @return The valid string input from the user.
     */
    public static String getValidString(String msg) {
        String input;
        boolean quit = false;
        do {
            System.out.print(msg);
            input = sc.nextLine();
            if (input.isEmpty()) {
                System.out.println("Value can not null");

            } else {
                quit = true;
            }

        } while (!quit);
        return input;
    }
    /**
     * Prompts the user for a valid flower description that is between 3 and 50 characters long.
     * 
     * @return The valid flower description from the user.
     */
    public static String getValidDescription() {
        String input;
        boolean quit = false;
        do {
            input = getValidString("Enter Name: ");
            if (input.length() >= 3 && input.length() <= 50) {
                quit = true;
            }
            else {
                System.out.println("invalid Name(3-50 charecters).");
            }
        } while (!quit);
        return input;
    }
    private static final String dateFormat = "(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])/(19|20)\\d\\d";

    /**
     * Reads a date input from the user and validates its format.
     * 
     * @param msg The message to display to the user.
     * @return The valid date input from the user.
     */
    public static String getDate(String msg) {
        boolean quit = false;
        String date = null;
        while (!quit) {
            try {
                System.out.print(msg);
                date = sc.nextLine();
                Pattern pt = Pattern.compile(dateFormat);
                if (pt.matcher(date).find()&& isValidDate(date)) {
                    quit = true;
                    break;
                }
                throw new Exception();
            } catch (Exception ex) {
                System.out.println("Wrong date format!");
            }
        }
        return date;
    }

    /**
     * Checks if a given date is valid.
     * 
     * @param date The date to check in the format "dd-mm-yyyy".
     * @return true if the date is valid, false otherwise.
     */
    public static boolean isValidDate(String date) {
        String[] split = date.split("[-/. ]");
        int day = Integer.parseInt(split[1]);
        int month = Integer.parseInt(split[0]);
        int year = Integer.parseInt(split[2]);
        int maxDay = 30;
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            maxDay = 31;
        }
        if (month == 2) {
            if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) {
                maxDay = 29;
            } else {
                maxDay = 28;
            }
        }
        return day <= maxDay;
    }
    
    /**
     * Prompts the user for a positive integer value.
     * 
     * @param message The message to display to the user.
     * @return The positive integer value entered by the user.
     */
    public static int getPositiveValue(String message) {
        int input = 0;

        boolean isValidInput;
        do {
            System.out.print(message);
            String userInput = sc.nextLine();

            try {
                input = Integer.parseInt(userInput);
                if (input > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("Value must be a positive number");
                    isValidInput = false;
                }
            } catch (Exception e) {
                System.out.println("Invalid value");
                isValidInput = false;

            }
        } while (!isValidInput);

        return input;
    }
    
    /**
     * Checks if the end date is after the start date.
     * 
     * @param startDate The start date.
     * @param endDate The end date.
     * @return true if the end date is after the start date, false otherwise.
     * @throws ParseException if the date parsing fails.
     */
    public static boolean checkBeforeAfter(String startDate, String endDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");

        if (sdf.parse(endDate).before(sdf.parse(startDate))) {

            return false;
        }
        return true;
    }
    
    /**
     * Parses a string to a Date object.
     * 
     * @param dateString The string representation of the date.
     * @return The parsed Date object.
     * @throws ParseException if the date parsing fails.
     */
    public static Date parseStringToDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
        Date date = sdf.parse(dateString);
        return date;
    }
    
    /**
     * Prompts the user for a formatted string input based on a given pattern.
     * 
     * @param message The message to display to the user.
     * @param pattern The pattern that the input should match.
     * @return The valid formatted string input from the user.
     */
    public static String getFormattedString(String message, String pattern) {
        String input;

        do {
            System.out.print(message);
            input = sc.nextLine();
            if (!input.matches(pattern)) {
                System.out.println("Input Invalid");
            }

        } while (!input.matches(pattern) || input.isEmpty());
        return input;
    }
}
