package service;

import model.Cake;
import model.Order;

public class StandardOrderService {

    public Order createOrder(Cake cake,
                             int quantity,
                             boolean delivery,
                             String address,
                             String phone,
                             String branch,
                             String pickupDate,
                             String pickupTime){

        Order order = new Order();

        order.setCake(cake);

        order.setQuantity(quantity);

        if(delivery){

            order.setFulfillmentMethod("Delivery");

            order.setAddress(address);

            order.setPhoneNumber(phone);

        }

        else{

            order.setFulfillmentMethod("Pickup");

            order.setBranch(branch);

            order.setPickupDate(pickupDate);

            order.setPickupTime(pickupTime);

        }

        return order;

    }

}