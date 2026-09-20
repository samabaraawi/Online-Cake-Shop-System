package service;

import model.Order;

public class ReportService {

    private final OrderService orderService =
            OrderService.getInstance();

    public int getTotalOrders() {

        return orderService.getAllOrders().size();

    }

    public int getPendingOrders() {

        int count = 0;

        for (Order order : orderService.getAllOrders()) {

            if (order.getOrderStatus().equalsIgnoreCase("Pending")) {
                count++;
            }

        }

        return count;
    }

    public int getCompletedOrders() {

        int count = 0;

        for (Order order : orderService.getAllOrders()) {

            if (order.getOrderStatus().equalsIgnoreCase("Delivered")
                    || order.getOrderStatus().equalsIgnoreCase("Paid")) {
                count++;
            }

        }

        return count;
    }

    public int getCancelledOrders() {

        int count = 0;

        for (Order order : orderService.getAllOrders()) {

            if (order.getOrderStatus().equalsIgnoreCase("Cancelled")
                    || order.getOrderStatus().equalsIgnoreCase("Rejected")) {
                count++;
            }

        }

        return count;
    }

    public double getTotalSales() {

        double total = 0;

        for (Order order : orderService.getAllOrders()) {

            if (order.getOrderStatus().equalsIgnoreCase("Delivered")
                    || order.getOrderStatus().equalsIgnoreCase("Paid")) {

                total += order.getCake().getPrice() * order.getQuantity();

            }

        }

        return total;
    }
}