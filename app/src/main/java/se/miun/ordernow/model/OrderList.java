package se.miun.ordernow.model;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.miun.ordernow.view.OrderStatus;

public class OrderList {
    private ArrayList<OrderItem> list;

    public OrderList() {
        list = new ArrayList<>();
    }

    public ArrayList<OrderItem> getList() {
        return list;
    }
    public void add(OrderItem element) {
        if(list.size() == 0) {
            list.add(element);
            return;
        }
        int i = 0;
        for (; i < list.size(); ++i) {
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

    // Updates state by changing status for orders that are ready to done
    // And sends the next orders with the next ordertype to kitchen by changing status from hold to cook.
    public void updateOrderStatus(final OrderStatus orderStatusActivity) {
        boolean updated = false;

        for(OrderItem order: list) {
            OrderItem.Status currentStatus = order.getStatus();
            OrderItem.Type currentType = order.getType();

            switch(currentStatus) {
                case HOLD: {
                    // Send all order with the same type to kitchen via API.
                    // Find all orders with same type and add them so the list that will be sent over API.
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
                    if(currentType == OrderItem.Type.VARMRÄTT) {
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

    public void printList() {
        for(OrderItem o: list) {
            System.out.println(o.toString());
        }
    }
}
