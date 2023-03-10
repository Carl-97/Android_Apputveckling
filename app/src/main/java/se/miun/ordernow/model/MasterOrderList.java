package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.controller.MainActivity;
import se.miun.ordernow.controller.OrderStatusNotification;
import se.miun.ordernow.view.KitchenMenuActivity;
import se.miun.ordernow.view.OrderStatus;

public class MasterOrderList {
    public static final int MAXIMUM_TABLES = 8;
    private static List<OrderList> masterList = null;

    public MasterOrderList() {
        if(masterList == null) {
            masterList = new ArrayList<>();
            for (int i = 0; i < MAXIMUM_TABLES; ++i) {
                masterList.add(new OrderList());
            }
        }
    }

    public void clear() {
        masterList.clear();
        for (int i = 0; i < MAXIMUM_TABLES; ++i) {
            masterList.add(new OrderList());
        }
    }

    public void clearOrderList(int index) {
        masterList.get(index).getList().clear();
    }

    public OrderList getOrderList(int tableIndex) {
        return masterList.get(tableIndex);
    }

    // I have seen better looking functions...
    // ToDo: This should add new orders that have come in from other clients/waiters, And also update existing orders that have been completed in the kitchen.
    // How should we handle this?? Should MasterOrderList be synchronized between multiple clients
    // Or should they be local only, this way we get that each waiter only receives notification for their tables and not ALL.
    // Either way, it seems to me that we need to have a unique identifier for each OrderItem so we know which item was updated
    // as there can exist multiple items from the same table that "come from" the same MenuItem and has the same description.
    public void updateOrderStatusByList(List<OrderItem> inputList) {
        boolean updated = false;

        OrderStatusNotification notification = new OrderStatusNotification(MainActivity.getContext());
        for(OrderItem inputItem: inputList) {
            if(!inputItem.hasBeenCooked()) {
                continue;
            }
            for(OrderItem localItem: masterList.get(inputItem.getTableNumber() - 1).getList()) {
                if(inputItem.equals(localItem)) {
                    if(localItem.getStatus() == OrderItem.Status.COOK) {
                        // Send notification here also
                        localItem.nextStatus();

                        notification.add(localItem);
                    }
                    break;
                }
            }
        }
        // Kitchen should not get notifications.
        //if(!KitchenMenuActivity.active)
        notification.execute();

        OrderStatus.updateView();
    }
}
