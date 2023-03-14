package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.KitchenMenuActivity;
import se.miun.ordernow.view.OrderItemAdapter;

// KitchenOrderList contains a list of Order which in itself contains a list of OrderItems.
// This is the list that kitchen uses to display what OrderItems should be cooked.
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

    public Order getOrder(int index) {
        return orderList.get(index);
    }
    public int size() {
        return orderList.size();
    }
    public void remove(int index) {
        orderList.remove(index);
    }

    // Adds an OrderItem if it does not yet exist in the orderList.
    public void addItem(OrderItem item) {
        if(item.hasBeenCooked())
            return;

        int orderIndex = 0;
        boolean orderExists = false;
        for(int i = 0; i < orderList.size(); ++i) {
            if(item.getTableNumber() == orderList.get(i).getOrderNumber()) {
                orderExists = true;
                orderIndex = i;
                break;
            }
        }

        boolean updated = false;
        if(!orderExists) {
            orderList.add(new Order(item.getTableNumber(), item.getTable()));
            orderIndex = orderList.size() - 1;
            updated = true;
        }

        Order order = orderList.get(orderIndex);
        boolean added = order.addItem(item);
        if(added)
            updated = true;

        if(updated)
            KitchenMenuActivity.updateAdapter();
    }

    // Removes Order objects with an empty list.
    public void removeEmptyOrders() {
        for(int i = 0; i < orderList.size(); ++i) {
            if(orderList.get(i).size() == 0) {
                orderList.remove(i);
            }
        }
    }

    // Some weird stuff happens with the kitchen adapters that use reference to our lists.
    // So sometimes they are left with a reference that no longer works, in that case they can call us for a new one.
    // Given that there exists an Order that is bound to the given tableNumber.
    public List<OrderItem> requestNewReference(int tableNumber) {
        for(Order order: orderList) {
            if(order.getOrderNumber() == tableNumber)
                return order.getItems();
        }
        return null;
    }

    // For debugging
    public void printCurrentOrderNumbers() {
        System.out.print("{");
        for(int i = 0; i < orderList.size(); ++i) {
            if(i != 0)
                System.out.print(", ");
            System.out.print(orderList.get(i).getOrderNumber());
        }
        System.out.println("}");
    }

    // For debugging
    public static void print() {
        System.out.print("KitchenOrderList content: [");
        for(int i = 0; i < orderList.size(); ++i) {
            if(i != 0)
                System.out.print(", ");

            Order order = orderList.get(i);
            System.out.print("{Order: " + order.getOrderNumber() + ", array: [");
            for(int j = 0; j < order.size(); ++j) {
                if(j != 0)
                    System.out.print(", ");
                OrderItem item = order.getItems().get(j);
                System.out.print("{" + item.getName() + ", " + item.getId() + "}");
            }
            System.out.print("]}");
        }
        System.out.println("]");
    }
}
