package se.miun.ordernow.model;

import java.util.List;

public class Table {
    private int tableNumber;
    private boolean tableStatus;

    public Table(int tableNumber, boolean tableStatus) {
        this.tableNumber = tableNumber;
        this.tableStatus = tableStatus;
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
}
