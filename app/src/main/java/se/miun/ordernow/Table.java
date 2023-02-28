package se.miun.ordernow;

import java.util.List;

public class Table {
    private int tableNumber;
    private boolean tableStatus;
    private List<Order> orders;

    public Table(int tableNumber, boolean tableStatus, List<Order> orders) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
        this.orders =orders;

    }
    public Table(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public boolean isTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(boolean tableStatus) {
        this.tableStatus = tableStatus;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
