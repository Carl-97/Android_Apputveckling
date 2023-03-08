package se.miun.ordernow.model;


import java.util.ArrayList;
import java.util.List;

public class Order {
    private int orderNumber;
    private List<OrderItem> items;
    private Table table;


    public Table getTable() {
        return table;
    }

    public Order(int orderNumber, List<OrderItem> items, Table table) {
        this.orderNumber = orderNumber;
        this.items = items;
        this.table = table;
    }

    public Order(int orderNumber, Table table) {
        this.orderNumber = orderNumber;
        this.table = table;
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        boolean exists = false;
        for(int i = 0; i < items.size(); ++i) {
            if(item.getId() == items.get(i).getId()) {
                System.out.println("This item already exists: " + item.getName() + " with id: " + item.getId());
                exists = true;
                break;
            }
        }
        if(!exists) {
            System.out.println("Added item to an order");
            items.add(item);
        }
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
