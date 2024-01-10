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
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * OrderManagement class represents a set of orders and provides various
 * operations on them.
 *
 */
public class OrderManagement extends HashSet<Order> {

    /**
     * getOrderDetailById method retrieves an OrderDetail object from a set of
     * OrderDetail objects by its ID.
     *
     * @param ods The set of OrderDetail objects to search.
     * @param id The ID of the OrderDetail object to find.
     * @return The found OrderDetail object, or null if not found.
     */
    public void displayAllOrderDetailById(String id) {
        Order orderFind = null;
        for (Order order : this) {
            if (order.getOrderId().equals(id)) {
                orderFind = order;
            }
        }
        if (orderFind == null) {
            return;
        }
        for (OrderDetail od : orderFind.getOrderDetails()) {
            System.out.println(od);
        }
    }
    public OrderDetail getOrderDetailById(HashSet<OrderDetail> ods, String id) {
        for (OrderDetail od : ods) {
            if (od.getOrderDetailId().equals(id)) {
                return od;
            }
        }
        return null;
    }

    /**
     * createOrderDetail method creates a new OrderDetail object by taking input
     * from the user.
     *
     * @param flowerManagement The FlowerManagement object to retrieve flower
     * information.
     * @param orderDetailSet The set of existing OrderDetail objects.
     * @return The newly created OrderDetail object.
     */
    public OrderDetail createOrderDetail(FlowerManagement flowerManagement, HashSet<OrderDetail> orderDetailSet) {
        String orderDetailId;
        String flowerId;
        boolean quit;
        do {
            quit = true;
            orderDetailId = InputHandler.getValidString("Enter Order Detail Id: ");
            if (getOrderDetailById(orderDetailSet, orderDetailId) != null) {
                System.out.println("order detail already exist enter another id.");
                quit = false;
            }
        } while (!quit);

        do {
            quit = true;
            flowerId = InputHandler.getValidString("Enter flower id: ");
            if (flowerManagement.findFlowerById(flowerId, false) == null) {
                quit = false;
                System.out.println("Available flower list: ");
                for (Flower flower : flowerManagement) {
                    System.out.println(flower.getId());
                }

            }
        } while (!quit);

        int quanity = InputHandler.getPositiveValue("Enter quanity: ");
        int flowerCost = quanity * flowerManagement.findFlowerById(flowerId, false).getUnitPrice();
        OrderDetail od = new OrderDetail(orderDetailId, flowerId, quanity, flowerCost);
        return od;
    }

    /**
     * createOrder method creates a new Order object by taking input from the
     * user.
     *
     * @param orderId The ID of the new order.
     * @param flowerManagement The FlowerManagement object to retrieve flower
     * information.
     */
    public void createOrder(String orderId, FlowerManagement flowerManagement) {
        
            HashSet<OrderDetail> orderDetailSet = new HashSet<>();
        int orderTotal = 0;
        int flowerCount = 0;
        String orderDate = InputHandler.getDate("Enter order date: ");
        String customerName = InputHandler.getValidString("Enter customer name: ");
        do {
            OrderDetail orderDetail = createOrderDetail(flowerManagement, orderDetailSet);
            if (orderDetail == null) {
                return;
            } else {
                orderDetailSet.add(orderDetail);
            }

        } while (InputHandler.yesNoConfirm("Do you want to add more order detail(y/n)? : "));
        for (OrderDetail orderDetail : orderDetailSet) {
            orderTotal += orderDetail.getFlowerCost();
            flowerCount += orderDetail.getQuanity();
        }

        Order order = new Order(orderId, orderDate, customerName, orderTotal, flowerCount, orderDetailSet);
        this.add(order);
        
        
        

    }

    /**
     * displayOrders method displays the orders within a given date range.
     *
     * @param startDate The start date of the range.
     * @param endDate The end date of the range.
     * @throws ParseException If there is an error parsing the dates.
     */
    public void displayOrders(String startDate, String endDate) throws ParseException {
        int count = 0;
        int flowerCountTotal = 0;
        int allOrderTotal = 0;
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.format("|%-5s|%-12s|%-14s|%-30s|%-15s|%-30s|\n", "No.", "Order Id", "Order Date", "Customer", "Flower count", "Order Total");
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        for (Order o : this) {
            if (InputHandler.checkBeforeAfter(startDate, o.getOrderDate()) && InputHandler.checkBeforeAfter(o.getOrderDate(), endDate)) {
                flowerCountTotal += o.getFlowerCount();
                allOrderTotal += o.getOrderTotal();
                count++;
                System.out.format("|%-5d|%-12s|%-14s|%-30s|%-15s|%-30s|\n",
                        count, o.getOrderId(), o.getOrderDate(), o.getCustomerName(), o.getFlowerCount(), o.getOrderTotal());

            }
        }
        System.out.println("----------------------------------------------------------------------------------------------------------------");
        System.out.printf("|%-5s|%-12s|%-14s|%-30s|%-15s|%-30s|\n", "", "Total", "", "", flowerCountTotal, allOrderTotal);
        System.out.println("----------------------------------------------------------------------------------------------------------------");
    }

    /**
     * sortByField method sorts the OrderManagement set based on a specified
     * field.
     *
     * @param orderSet The OrderManagement set to be sorted.
     * @param choice The user's choice for the field to sort by.
     * @return A sorted List of Order objects.
     */
    public List<Order> sortByField(HashSet<Order> orderSet, String choice) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        List<Order> sortedArr = new ArrayList<Order>(orderSet);
        boolean quit = true;
        do {
            switch (choice) {
                case "1":
                    sortedArr.sort(Comparator.comparing(Order::getOrderId));
                    break;
                case "2":
                    sortedArr.sort(Comparator.comparing((Order order) -> {
                        try {
                            return sdf.parse(order.getOrderDate());
                        } catch (Exception e) {
                            e.printStackTrace();
                            return null;
                        }
                    }));

                    break;
                case "3":
                    sortedArr.sort(Comparator.comparing(Order::getCustomerName));
                    break;
                case "4":
                    sortedArr.sort(Comparator.comparing(Order::getOrderTotal));
                    break;
                default:
                    System.out.println("Invalid option");
                    quit = false;
                    break;

            }
        } while (!quit && InputHandler.yesNoConfirm("Do you want to continue."));

        return sortedArr;
    }

    /**
     * sortByOrder method sorts the OrderManagement list based on ascending or
     * descending order.
     *
     * @param orderSet The OrderManagement list to be sorted.
     * @param choice The user's choice for ascending or descending order.
     * @return The sorted List of Order objects.
     */
    public List<Order> sortByOrder(List<Order> orderSet, String choice) {
        List<Order> sortedArr = new ArrayList<Order>(orderSet);
        boolean quit = true;
        do {
            switch (choice) {
                case "1":

                    break;
                case "2":
                    Collections.reverse(sortedArr);

                    break;
                default:
                    System.out.println("Invalid option");
                    quit = false;
                    break;
            }
        } while (!quit && InputHandler.yesNoConfirm("Do you want to continue."));

        return sortedArr;
    }

    /**
     * saveToFile method saves the OrderManagement set to a file.
     *
     * @param filePath The path of the file to save to.
     */
    public void saveToFile(String filePath) {
        if (this.isEmpty()) {
            System.out.println("Order's set empty!");
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
            System.out.println("Order's set has been saved!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * loadFromFile method loads the OrderManagement set from a file.
     *
     * @param filePath The path of the file to load from.
     * @throws ClassNotFoundException If the class of a serialized object cannot
     * be found.
     */
    public void loadFromFile(String filePath) throws ClassNotFoundException {
        if (!this.isEmpty()) {
            this.clear();
        }
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Order's set is empty!");
                return;
            }
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Order order;
            boolean quit = false;
            while (!quit) {
                try {
                    order = (Order) objectInputStream.readObject();
                    this.add(order);
                } catch (EOFException e) {
                    quit = true;
                    break; // Reached the end of the file, break out of the loop(End of File exception)
                }
            }
            fileInputStream.close();
            objectInputStream.close();
            System.out.println("Order's set has been loaded!");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
