package se.miun.ordernow.model;

// Represents a table in the restaurant
public class Table {
    private int tableId = 0;
    private int tableSize = 0;

    public Table(int id, int size) {
        this.tableId = id;
        this.tableSize = size;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTableSize() {
        return tableSize;
    }

    public void setTableSize(int tableSize) {
        this.tableSize = tableSize;
    }

    // For debugging
    public void print() {
        System.out.print("table id: ");
        if(tableId == 0)
            System.out.println("null");
        else
            System.out.println(tableId);

        System.out.print("table size: ");
        if(tableSize == 0)
            System.out.println("null");
        else
            System.out.println(tableSize);
    }
}
