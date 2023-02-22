package se.miun.ordernow;


public class Order {
    enum Status {
        HOLD, COOK, READY, DONE;

        public Status next() {
            if(ordinal() > values().length - 2)
                return DONE;
            return values()[ordinal() + 1];
        }
    }
    enum OrderType {
        FÖRRÄTT, VARMRÄTT, EFTERÄTT
    }
    private String name;
    private OrderType type;
    private String description;
    private Status status;

    public Order(String name, OrderType type, String description) {
        this.name = name;
        this.type = type;
        this.description = description;
        status = Status.HOLD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void progressStatus() {
        status = status.next();
    }

    public String toString() {
        return name + " " + description;
    }
}
