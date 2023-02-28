package se.miun.ordernow;


import java.util.ArrayList;

public class OrderList {
    private ArrayList<OrderItem> list;

    public OrderList() {
        list = new ArrayList<>();
    }

    public ArrayList<OrderItem> getList() {
        return list;
    }
    public void add(OrderItem element) {
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

    public OrderItem get(int index) {
        return list.get(index);
    }

    // Updates state by changing status for orders that are ready to done
    // And sends the next orders with the next ordertype to kitchen by changing status from hold to cook.
    public void updateState() {
        OrderItem.Type currentType = OrderItem.Type.FÖRRÄTT;
        boolean updated = false;
        for(OrderItem o: list) {
            OrderItem.Status currentStatus = o.getStatus();
            switch(currentStatus) {
                case HOLD:
                case COOK: {
                    // Send all hold orders of same type to kitchen
                    currentType = o.getType();
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            nextOrder.nextStatus();
                        }
                    }
                    updated = true;
                    break;
                }// Wait for kitchen
                case READY: {
                    currentType = o.getType();
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            nextOrder.nextStatus();
                        }
                    }
                    // When the main dishes have been delivered, desserts should not be sent to kitchen immidietly.
                    if(currentType == OrderItem.Type.VARMRÄTT) {
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

    public int size() {
        return list.size();
    }

    public void printList() {
        for(OrderItem o: list) {
            System.out.println(o.toString());
        }
    }
}
