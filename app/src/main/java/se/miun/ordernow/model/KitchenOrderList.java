package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.KitchenMenuActivity;

public class KitchenOrderList {
    private static List<Order> orderList;

    public KitchenOrderList() {
        if(orderList == null) {
            orderList = new ArrayList<>();
        }
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void addItem(OrderItem item) {
        System.out.println("Trying to add item: " + item.getName());
        boolean orderExists = false;
        for(int i = 0; i < orderList.size(); ++i) {
            if(item.getTableNumber() == orderList.get(i).getOrderNumber()) {
                orderExists = true;
                break;
            }
        }

        if(!orderExists) {
            System.out.println("Added a new order with table number: " + item.getTableNumber());
            orderList.add(new Order(item.getTableNumber(), item.getTable()));
        }

        for(Order o: orderList) {
            if(o.getOrderNumber() == item.getTableNumber()) {
                o.addItem(item);
                break;
            }
        }
    }
}
