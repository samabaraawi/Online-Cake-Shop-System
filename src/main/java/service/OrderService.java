package service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Order;

public class OrderService {

    private static final OrderService instance = new OrderService();

    private static int nextOrderID = 1001;

    // جميع الطلبات
    private final ObservableList<Order> orders =
            FXCollections.observableArrayList();

    // آخر طلب (حتى ما نخرب الشاشات الحالية)
    private Order currentOrder;

    private OrderService() {

    }

    public static OrderService getInstance() {

        return instance;

    }

    public void createOrder(Order order) {

        order.setOrderID(nextOrderID++);

        order.setOrderStatus("Pending");

        order.setPaymentStatus("Pending");

        currentOrder = order;

        orders.add(order);

    }

    public Order getCurrentOrder() {

        return currentOrder;

    }

    public ObservableList<Order> getAllOrders() {

        return orders;

    }

    public Order findOrderByID(int orderID) {

        for (Order order : orders) {

            if (order.getOrderID() == orderID) {

                return order;

            }

        }

        return null;

    }

}