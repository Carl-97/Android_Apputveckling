package se.miun.ordernow.model;

import com.google.gson.annotations.SerializedName;

// Represents a table in the restaurant
public class Table {
    @SerializedName("id")
    private int id = 0;
    @SerializedName("tableSize")
    private int size = 0;

    public Table(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    // For debugging
    public void print() {
        System.out.print("table id: ");
        if(id == 0)
            System.out.println("null");
        else
            System.out.println(id);

        System.out.print("table size: ");
        if(size == 0)
            System.out.println("null");
        else
            System.out.println(size);
    }
}
