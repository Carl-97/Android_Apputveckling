package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

public class MenuItem {
    public enum Type {
        APPETIZER, MAINDISH, DESSERT
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

    public Type getCategory() {
        Type type;
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
            default:
                type = Type.APPETIZER;
        }
        return type;
    }

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

    public void print() {
        System.out.print("name: ");
        if(name == null)
            System.out.println("null");
        else
            System.out.println(name);

        System.out.print("id: ");
        if(id == 0)
            System.out.println("null");
        else
            System.out.println(id);

        System.out.print("description: ");
        if(description == null)
            System.out.println("null");
        else
            System.out.println(description);

        System.out.print("type: ");
        if(category == null)
            System.out.println("null");
        else
            System.out.println(category);

        System.out.print("price: ");
        if(price == 0)
            System.out.println("null");
        else
            System.out.println(price);
    }
}
