package se.miun.ordernow.model;

public class Table {
    private int tableId = 0;
    private int tablesize = 0;

    public Table(int id, int size) {
        this.tableId = id;
        this.tablesize = size;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public int getTablesize() {
        return tablesize;
    }

    public void setTablesize(int tablesize) {
        this.tablesize = tablesize;
    }

    public void print() {
        System.out.print("table id: ");
        if(tableId == 0)
            System.out.println("null");
        else
            System.out.println(tableId);

        System.out.print("table size: ");
        if(tablesize == 0)
            System.out.println("null");
        else
            System.out.println(tablesize);
    }
}
