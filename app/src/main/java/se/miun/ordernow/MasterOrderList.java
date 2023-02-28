package se.miun.ordernow;

import java.util.ArrayList;

public class MasterOrderList {
    public static final int MAXIMUM_TABLES = 8;
    private static ArrayList<OrderList> masterList = new ArrayList<>();

    public MasterOrderList() {
        if(masterList.size() != MAXIMUM_TABLES) {
            for (int i = 0; i < MAXIMUM_TABLES; ++i) {
                masterList.add(new OrderList());
            }
        }
        System.out.println("MasterOrder object created, size is " + masterList.size());
    }

    public OrderList getOrderList(int tableIndex) {
        return masterList.get(tableIndex);
    }

}
