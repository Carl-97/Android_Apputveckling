package se.miun.ordernow.model;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
import se.miun.ordernow.view.KitchenMenuActivity;
import se.miun.ordernow.view.OrderItemAdapter;
import se.miun.ordernow.view.OrderStatus;

public class ApiCommunicator {
    private static Api apiInstance = null;

    public ApiCommunicator() {
        if(apiInstance == null)
            apiInstance = RetrofitClient.getInstance().getMyApi();
    }

    public void fillMenuList() {
        Call<List<MenuItem>> call = apiInstance.getItems();
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                List<MenuItem> list = response.body();
                if(list == null) {
                    System.out.println("getMenuList() -> Null menulist response");
                    return;
                }

                MenuList menuList = new MenuList();
                menuList.addMenuList(list);
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                System.out.println("API Response Error: Fetching menu from api");
                System.out.println(t.getMessage());
            }
        });
    }

    public void fillTableList() {
        Call<List<Table>> call = apiInstance.getTables();
        call.enqueue(new Callback<List<Table>>() {
            @Override
            public void onResponse(Call<List<Table>> call, Response<List<Table>> response) {
                List<Table> tableList = response.body();
                if(tableList == null) {
                    System.out.println("fillTableList() -> Error: Api Response list was null");
                    return;
                }

                TableList localTableList = new TableList();
                localTableList.addTables(tableList);
            }

            @Override
            public void onFailure(Call<List<Table>> call, Throwable t) {
                System.out.println("API Response error while fetching tables. Filling TableList with fallback method.");
                TableList localTableList = new TableList();
                localTableList.addTables(TableList.DEFAULT_TABLE_COUNT, TableList.DEFAULT_TABLE_SIZE);
            }
        });
    }

    public void postOrders(List<OrderItem> orders, final OrderStatus orderStatusActivity) {
        System.out.println("Posting orders that belong to table " + orders.get(0).getTableNumber());
        Call<List<OrderItem>> call = apiInstance.postOrderItems(orders);
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(response.body() == null) {
                    System.out.println("postOrder() -> Error: Api Response list was null");
                    return;
                }
                if(response.body().size() != orders.size()) {
                    System.out.println("Error: Api Response list size does not match posted list?");
                    System.out.print("Received items: {");
                    for(OrderItem recievedItem: response.body()) {
                        System.out.print(recievedItem.getName() + ", ");
                    }
                    System.out.println("}");

                    System.out.print("Posted items: {");
                    for(OrderItem postedItem: orders) {
                        System.out.print(postedItem.getName() + ", ");
                    }
                    System.out.println("}");
                }
                else {
                    List<OrderItem> responseList = response.body();
                    for(int i = 0; i < orders.size(); ++i) {
                        orders.get(i).setId(responseList.get(i).getId());
                    }
                }

                for(OrderItem item: orders) {
                    item.nextStatus();
                }

                orderStatusActivity.updateView();
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                System.out.println("API Response Error: Posting orders to api");
                System.out.println(t.getMessage());
            }
        });
    }

    public void updateMasterOrderList() {
        Call<List<OrderItem>> call = apiInstance.getOrders();
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                List<OrderItem> list = response.body();
                if(list == null) {
                    System.out.println("getAllOrder() -> Null orderlist response");
                    return;
                }
                System.out.println("Fetching order from database, list size: " + list.size());


                MasterOrderList masterList = new MasterOrderList();
                masterList.updateOrderStatusByList(list);

                KitchenOrderList kitchenOrderList = new KitchenOrderList();
                for(OrderItem item: list) {
                    kitchenOrderList.addItem(item);
                }
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                System.out.println("API Response Error: Fetching orders from api");
                System.out.println(t.getMessage());
            }
        });
    }

    public void postOrderItemCooked(OrderItemAdapter adapter, List<OrderItem> list, int index, OrderItem orderItem) {
        Call<OrderItem> call = apiInstance.postOrderItemReady(orderItem.getId());
        printRequestBody(orderItem.getId());
        call.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                OrderItem changedItem = response.body();
                if(changedItem == null) {
                    System.out.println("Null response");
                    return;
                }
                System.out.println("Within api response -> Removing item: " + changedItem.getName());

                adapter.removeItem(changedItem);
                KitchenMenuActivity.updateAdapter();
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
                System.out.println("API Response Error: Posting OrderItem cooked to api");
                System.out.println(t.getMessage());
                System.out.println(t.getLocalizedMessage());
                System.out.println(t.toString());
            }
        });
    }

    private void printRequestBody(Object obj) {
        if(obj == null)
            return;
        Gson converter = new Gson();
        System.out.print("Request body: ");
        System.out.println(converter.toJson(obj));
    }
}
