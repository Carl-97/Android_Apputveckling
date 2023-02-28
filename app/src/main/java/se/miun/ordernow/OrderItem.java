package se.miun.ordernow;

// Represent an item from an Order.
public class OrderItem {
    enum Status {
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
    enum Type {
        FÖRRÄTT, VARMRÄTT, EFTERÄTT
    }
    private long id;
    private String name;
    private Type type;
    private String description;
    private Status status;

    private static long idCounter = 0;

    public OrderItem(String name, Type type, String description) {
        this.id = idCounter;
        this.name = name;
        this.type = type;
        this.description = description;
        status = Status.HOLD;

        // Increase id counter for next object.
        ++idCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
    public void nextStatus() { status = status.next(); }


    public String toString() {
        return name + " " + description;
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
}
