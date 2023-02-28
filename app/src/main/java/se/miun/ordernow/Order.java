package se.miun.ordernow;


import java.util.List;

public class Order {
    private int orderNumber;
    private List<Item> items;
    private Table table;


    public Table getTable() {
        return table;
    }

    public Order(int orderNumber, List<Item> items, Table table) {
        this.orderNumber = orderNumber;
        this.items = items;
        this.table = table;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
