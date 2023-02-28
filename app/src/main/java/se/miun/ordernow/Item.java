package se.miun.ordernow;

public class Item {
    private String itemName;
    private int itemPrice;
    private String itemChanges;
    private String itemType;

    public Item(String itemName, String itemChanges) {
        this.itemName = itemName;
        this.itemChanges = itemChanges;
    }

    public Item(String itemName, String itemChanges, String itemType) {
        this.itemName = itemName;
        this.itemChanges = itemChanges;
        this.itemType = itemType;
    }

    public void setItemChanges(String itemChanges) {
        this.itemChanges = itemChanges;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemChanges() {
        return itemChanges;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
