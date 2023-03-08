package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

// Represent an item from an Order.
public class OrderItem {
    public enum Status {
        HOLD, COOK, READY, DONE;

        public Status next() {
            if(ordinal() > values().length - 2)
                return DONE;
            return values()[ordinal() + 1];
        }

        public boolean lessThan(Status status) {
            return ordinal() < status.ordinal();
        }
    }
    @SerializedName("ordersId")
    private int orderItemId = 0;
    @SerializedName("note")
    private String description;

    @SerializedName("cooked")
    private boolean hasBeenCooked = false;

    @SerializedName("itemsByItemFk")
    private MenuItem menuItem;
    @SerializedName("dinnertableByTableFk")
    private Table table;

    private Status status;


    public OrderItem(MenuItem menuItem, String description, int tableIndex) {
        this.menuItem = menuItem;
        // Yuck conversion
        this.description = description;
        TableList tableList = new TableList();
        this.table = tableList.getTable(tableIndex);
        status = Status.HOLD;
    }

    public boolean hasBeenCooked() {
        return hasBeenCooked;
    }

    public int getId() {
        return orderItemId;
    }
    public void setId(int id) {
        orderItemId = id;
    }
    public MenuItem getMenuItem() {
        return menuItem;
    }
    public String getName() {
        return menuItem.getName();
    }

    public void setName(String name) {
        menuItem.setName(name);
    }

    public MenuItem.Type getType() {
        return menuItem.getCategory();
    }

    public void setType(String type) {
        menuItem.setCategory(type);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public long getMenuItemID() {
        return menuItem.getId();
    }

    public int getTableNumber() {
        if(table == null)
            return 0;

        return table.getTableId();
    }

    public Table getTable() {
        return table;
    }

    public void nextStatus() { status = status.next(); }


    public String toString() {
        return menuItem.getName() + " " + description;
    }

    public boolean lessThan(OrderItem rhs) {
        int difference = this.getType().ordinal() - rhs.getType().ordinal();
        // If Type is the same, look at status.
        if(difference == 0) {
            difference = this.getStatus().ordinal() - rhs.getStatus().ordinal();
        }

        if(difference >= 0)
            return false;
        return true;
    }

    public boolean equals(OrderItem rhs) {
        return getId() == rhs.getId();
    }

    public void print() {
        if(menuItem != null) {
            menuItem.print();
        }
        else
            System.out.println("MenuItem: null");

        System.out.print("Order description: ");
        if(description == null)
            System.out.println("null");
        else
            System.out.println(description);

        if(table != null)
            table.print();
        else
            System.out.println("Table: null");
    }
}
