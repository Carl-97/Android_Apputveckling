package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.model.OrderList;
//ToDo: Does not work atm, communication between OrderMenu and OrderStatus not working.
public class MasterOrderList {
    public static final int MAXIMUM_TABLES = 8;
    private List<OrderList> masterList = null;

    public MasterOrderList() {
        if(masterList == null) {
            masterList = new ArrayList<>();
            for (int i = 0; i < MAXIMUM_TABLES; ++i) {
                masterList.add(new OrderList());
            }
        }
    }

    public OrderList getOrderList(int tableIndex) {
        return masterList.get(tableIndex);
    }
}
