package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.KitchenMenuActivity;

public class KitchenOrderList {
    private static List<Order> orderList;

    // In case the POST from kitchen happens at the same time as
    // the GET from background updater.
    private static List<OrderItem> recentlyRemovedOrders;

    public KitchenOrderList() {
        if(orderList == null) {
            orderList = new ArrayList<>();
            recentlyRemovedOrders = new ArrayList<>();
        }
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public List<OrderItem> getRecentlyRemovedOrders() {
        return recentlyRemovedOrders;
    }

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

    public void removeEmptyOrders() {
        for(int i = 0; i < orderList.size(); ++i) {
            if(orderList.get(i).size() == 0) {
                orderList.remove(i);
            }
        }
    }
}
