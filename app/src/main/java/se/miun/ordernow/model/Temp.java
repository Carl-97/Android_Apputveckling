package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

public class Temp {
    @SerializedName("itemId")
    private int itemId;
    @SerializedName("description")
    private String description;
    @SerializedName("itemcategory")
    private String itemcategory;
    @SerializedName("name")
    private String name;
    @SerializedName("price")
    private Integer price;

    public Temp(int itemId, String description, String itemcategory, String name, Integer price) {
        this.itemId = itemId;
        this.description = description;
        this.itemcategory = itemcategory;
        this.name = name;
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemcategory() {
        return itemcategory;
    }

    public void setItemcategory(String itemcategory) {
        this.itemcategory = itemcategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
