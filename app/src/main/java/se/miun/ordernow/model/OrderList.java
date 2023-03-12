package se.miun.ordernow.model;


import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.OrderStatusActivity;

// Represents a list OrderItems
public class OrderList {
    private ArrayList<OrderItem> list;

    public OrderList() {
        list = new ArrayList<>();
    }

    public ArrayList<OrderItem> getList() {
        return list;
    }
    // Adds an OrderItem in a sorted fashion
    public void add(OrderItem element) {
        if(list.size() == 0) {
            list.add(element);
            return;
        }

        for (int i = 0; i < list.size(); ++i) {
            if(element.lessThan(list.get(i))) {
                list.add(i, element);
                return;
            }
        }
        list.add(element);
    }

    public OrderItem get(int index) {
        return list.get(index);
    }

    // Updates the status of OrderItems in list.
    // Prioritise "lesser" types. So Appetizer -> Main -> Dessert.
    // Focuses on one type at a time. So if there exists Appetizers in list, it will update only those types until they are done.
    // When an OrderItem is in HOLD status we send them to the api, and upon a successful api response we update the status.
    public void updateOrderStatus(final OrderStatusActivity orderStatusActivity) {
        boolean updated = false;

        for(OrderItem order: list) {
            OrderItem.Status currentStatus = order.getStatus();
            MenuItem.Type currentType = order.getType();

            switch(currentStatus) {
                case HOLD: {
                    // Send all order with the same type to kitchen via API.
                    // Find all orders with same type (and status) and add them so the list that will be sent over API.
                    List<OrderItem> ordersToSend = new ArrayList<>();
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            ordersToSend.add(nextOrder);
                        }
                    }
                    // Send to API
                    ApiCommunicator apiCommunicator = new ApiCommunicator();
                    apiCommunicator.postOrders(ordersToSend, orderStatusActivity);

                    updated = true;
                    break;
                }
                case COOK: {
                    // If orders are being prepared the button should not work.
                    // As we are waiting for kitchen to complete the orders.
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            nextOrder.nextStatus();
                        }
                    }
                }
                case READY: {
                    // If orders are ready they should be tagged as Done
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            nextOrder.nextStatus();
                        }
                    }
                    // When the main dishes have been delivered, desserts should not be sent to kitchen immediately.
                    // So we exit the for loop.
                    if(currentType == MenuItem.Type.MAINDISH) {
                        updated = true;
                    }
                    break;
                }
                case DONE: {
                    // Order is done, we do nothing.
                }
            }
            if(updated) {
                break;
            }
        }
    }

    public int size() {
        return list.size();
    }
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public void printList() {
        for(OrderItem o: list) {
            System.out.println(o.toString());
        }
    }

    // Checks if there exists orderitems that are in a ready state.
    public boolean ordersReady() {
        for(OrderItem item: list) {
            if(item.getStatus() == OrderItem.Status.READY) {
                return true;
            }
        }
        return false;
    }

    // Takes in position of the OrderItem that we want to go from.
    // Returns the index for the next OrderItem thats type is greater than that of the OrderItem that was given.
    private int getNextType(int index) {
        MenuItem.Type type = list.get(index).getType();
        for(int i = index; i < list.size(); ++i) {
            if(list.get(i).getType() != type)
                return i;
        }

        return -1;
    }

    // Code below is for when we have sync list structure.
    public void sendAllOrders(final OrderStatusActivity activity) {
        if(list.size() == 0)
            return;

        OrderItem firstOrder = list.get(0);
        MenuItem.Type currentType = firstOrder.getType();

        for(OrderItem order: list) {
            if(currentType == order.getType()) {
                order.nextStatus();
            }
        }

        ApiCommunicator apiCommunicator = new ApiCommunicator();
        apiCommunicator.postOrders(list, activity);

        activity.updateView();
    }

    public void nextListState(final OrderStatusActivity activity) {
        if(list.size() == 0)
            return;

        int orderIndex = 0;
        OrderItem order = list.get(orderIndex);
        OrderItem.Status currentStatus = order.getStatus();
        MenuItem.Type currentType = order.getType();

        // Update all order of same type that is "READY"
        if(currentStatus == OrderItem.Status.READY) {
            for(OrderItem orderItem: list) {
                if(orderItem.getType() == currentType) {
                    orderItem.nextStatus();
                }
            }

            if(order.getType() == MenuItem.Type.MAINDISH)
                return;
        }

        orderIndex = getNextType(orderIndex);
        if(orderIndex == -1)
            return;

        for(OrderItem orderItem: list) {
            if(orderItem.getType() == currentType) {
                orderItem.nextStatus();
            }
        }
    }
}
