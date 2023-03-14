package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

// This represents a MenuItem in our order menu.
public class MenuItem {
    public enum Type {
        APPETIZER, MAINDISH, DESSERT, DRINK
    }
    @SerializedName("name")
    private String name;

    @SerializedName("itemId")
    private long id = 0;

    @SerializedName("description")
    private String description;

    @SerializedName("itemCategory")
    private String category;

    @SerializedName("price")
    private int price = 0;

    public MenuItem(String name, long id, String desc, String category, int price) {
        this.name = name;
        this.id = id;
        this.description = desc;
        this.category = category;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    // Converts our String variable to a Type enum.
    // Not certain if GSON will be confused if we send enums over api.
    public Type getCategory() {
        Type type = Type.DRINK;
        switch(category) {
            case "F":
                type = Type.APPETIZER;
                break;
            case "V":
                type = Type.MAINDISH;
                break;
            case "E":
                type = Type.DESSERT;
                break;
        }
        return type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Debugging stuff
    public boolean isValid() {
        boolean valid = true;
        if(name == null)
            valid = false;
        else if(description == null)
            valid = false;
        else if(category == null)
            valid = false;
        return valid;
    }
}
