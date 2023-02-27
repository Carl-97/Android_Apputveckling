package se.miun.ordernow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderList {
    private static ArrayList<Order> list = new ArrayList<>();

    public OrderList() {
    }

    public ArrayList<Order> getList() {
        return list;
    }

    // Updates state by changing status for orders that are ready to done
    // And sends the next orders with the next ordertype to kitchen by changing status from hold to cook.
    public void updateState() {
        Collections.sort(list, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                int diff = lhs.getType().ordinal() - rhs.getType().ordinal();
                if(diff > 1)
                    diff = 1;
                return diff;
            }
        });
        Order.OrderType currentType = Order.OrderType.FÖRRÄTT;
        for(Order o: list) {
            // Send orders to kitchen.
            // ToDo: Change to switch-case, and fix that hold status will not be updated by button.
            if(o.getStatus().lessThan(Order.Status.READY)) {
                currentType = o.getType();
                for(Order order: list) {
                    if(order.getType().equals(currentType)) {
                        order.setStatus(order.getStatus().next());
                    }
                }
                break;
            }
            else if(o.getStatus().equals(Order.Status.READY)) {
                currentType = o.getType();
                for(Order order: list) {
                    if(order.getType().equals(currentType)) {
                        order.setStatus(order.getStatus().next());
                    }
                }
            }
        }
    }

    public void printList() {
        for(Order o: list) {
            System.out.println(o.toString());
        }
    }

    private boolean hasApetizer() {
        for(Order o: list) {
            if(o.getStatus().equals(Order.OrderType.FÖRRÄTT))
                return true;
        }
        return false;
    }
}
