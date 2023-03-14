package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.controller.MainActivity;
import se.miun.ordernow.controller.OrderStatusNotification;
import se.miun.ordernow.view.OrderStatusActivity;

// This is our local list of all OrderItems currently in the system.
// (For now it only contains OrderItems that was created locally)
// MasterOrderList is a "centralized" list so we can have several Activities that uses the same data storage.
// It contains a list of OrderList where each OrderList is bound to a table. Ex. Table 4 always uses the fourth OrderList in the masterList.
public class MasterOrderList {
    public static final int MAXIMUM_TABLES = 8;
    private static List<OrderList> masterList = null;

    // We statically create 8 OrderList and hope we do now have more tables than that.
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


    // We compare the inputList with are masterList for status changes for each OrderItem.
    // If an OrderItem is updated we change its status to Ready and send a notification to the user.
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

                        updated = true;
                    }
                    break;
                }
            }
        }

        if(updated) {
            notification.execute();
            OrderStatusActivity.updateView();
        }
    }
}
