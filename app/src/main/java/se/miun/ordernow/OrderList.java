package se.miun.ordernow;


import java.util.ArrayList;

public class OrderList {
    private static ArrayList<Order> list = new ArrayList<>();

    public OrderList() {
    }

    public ArrayList<Order> getList() {
        return list;
    }
    public void addElement(Order element) {
        if(list.size() == 0) {
            list.add(element);
            return;
        }
        int i = 0;
        for (; i < list.size(); ++i) {
            if(element.lessThan(list.get(i))) {
                list.add(i, element);
                return;
            }
        }
        list.add(element);
    }

    // Updates state by changing status for orders that are ready to done
    // And sends the next orders with the next ordertype to kitchen by changing status from hold to cook.
    public void updateState() {
        Order.OrderType currentType = Order.OrderType.FÖRRÄTT;
        boolean updated = false;
        for(Order o: list) {
            Order.Status currentStatus = o.getStatus();
            switch(currentStatus) {
                case HOLD:
                case COOK: {
                    // Send all hold orders of same type to kitchen
                    currentType = o.getType();
                    for(Order nextOrder: list) {
                        if(nextOrder.getType().equals(currentType)) {
                            nextOrder.nextStatus();
                        }
                    }
                    updated = true;
                    break;
                }// Wait for kitchen
                case READY: {
                    currentType = o.getType();
                    for(Order nextOrder: list) {
                        if(nextOrder.getType().equals(currentType)) {
                            nextOrder.nextStatus();
                        }
                    }
                    // When the main dishes have been delivered, desserts should not be sent to kitchen immidietly.
                    if(currentType == Order.OrderType.VARMRÄTT) {
                        updated = true;
                    }
                    break;
                }
                case DONE: {
                    // Do nothing.
                }
            }
            if(updated) {
                break;
            }
        }
    }

    public void printList() {
        for(Order o: list) {
            System.out.println(o.toString());
        }
    }


    public String getButtonState() {
        for(Order order: list) {
            if(order.getStatus() == Order.Status.DONE)
                continue;
            return buttonStateString[order.getType().ordinal() * 3 + order.getStatus().ordinal()];
        }
        return "";
    }
    public String[] buttonStateString = {"Send Apetizer", "Waiting for Apetizer", "Apetizer delivered", "Send Main", "Waiting for Main", "Main delivered", "Send Dessert", "Waiting for Dessert", "Dessert delivered"};
}
