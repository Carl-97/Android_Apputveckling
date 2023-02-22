package se.miun.ordernow;

import java.util.ArrayList;

public class OrderList {
    private static ArrayList<Order> list = new ArrayList<>();

    public OrderList() {
    }

    public ArrayList<Order> getList() {
        return list;
    }
}
