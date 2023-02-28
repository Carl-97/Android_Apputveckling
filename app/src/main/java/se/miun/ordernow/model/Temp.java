package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

public class Temp {
    @SerializedName("name")
    public String name;

    @SerializedName("itemId")
    public long id;

    @SerializedName("description")
    public String description;

    @SerializedName("itemcategory")
    public String category;

    @SerializedName("price")
    public int price;
}
