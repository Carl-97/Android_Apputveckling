package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

public class MenuItem {
    public enum Type {
        FÖRRÄTT, VARMRÄTT, EFTERÄTT
    }
    @SerializedName("name")
    private String name;

    @SerializedName("itemId")
    private long id;

    @SerializedName("description")
    private String description;

    @SerializedName("itemCategory")
    private String category;

    @SerializedName("price")
    private int price;

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
        Type type = Type.FÖRRÄTT;
        switch(category) {
            case "V":
                type = Type.VARMRÄTT;
                break;
            case "E":
                type = Type.EFTERÄTT;
                break;
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
}
