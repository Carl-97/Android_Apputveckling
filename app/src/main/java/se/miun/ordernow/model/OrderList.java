package se.miun.ordernow.model;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.miun.ordernow.model.OrderItem;

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
                case HOLD: {
                    // Send all order with the same type to kitchen via API.
                    // ToDo: API call inside this class seems weird...
                    List<OrderItem> ordersToSend = new ArrayList<>();
                    currentType = o.getType();
                    // Find all orders with same type and add them so the list that will be sent over API.
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            ordersToSend.add(nextOrder);
                        }
                    }

                    Call<List<OrderItem>> call = RetrofitClient.getInstance().getMyApi().postOrderItems(ordersToSend);
                    call.enqueue(new Callback<List<OrderItem>>() {
                        @Override
                        public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                            List<OrderItem> responseList = response.body();
                            if(responseList == null) {
                                System.out.println("Null list response");
                                return;
                            }
                            for(OrderItem item: responseList) {
                                System.out.println(item.getName());

                                // Update all statuses of the sent orders in orderList
                                for(int i = 0; i < list.size(); ++i) {
                                    if(item.equals(list.get(i))) {
                                        list.get(i).nextStatus();
                                        break;
                                    }
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                            System.out.println("API Response Error: Posting orders to api");
                        }
                    });
                    updated = true;
                    break;
                }
                case COOK: {
                    // If orders are being prepared the button should not work.
                    // As we are waiting for kitchen to complete the orders.

                }
                case READY: {
                    // If orders are ready they should be tagged as Done
                    currentType = o.getType();
                    for(OrderItem nextOrder: list) {
                        if(nextOrder.getType().equals(currentType) && nextOrder.getStatus().equals(currentStatus)) {
                            nextOrder.nextStatus();
                        }
                    }
                    // When the main dishes have been delivered, desserts should not be sent to kitchen immediately.
                    // So we exit the for loop.
                    if(currentType == OrderItem.Type.VARMRÄTT) {
                        updated = true;
                    }
                    break;
                }
                case DONE: {
                    // Order is done, we do nothing.
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
