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

    // DEBUGGING:
    public void test(List<OrderItem> orders, final OrderStatus activity) {
        System.out.println("Trying to post following orders to api:");
        for(OrderItem item: orders) {
            System.out.println("{");
            item.print();
            System.out.println("}");
        }
        GsonConverterFactory factory = GsonConverterFactory.create();
        Gson gson = new Gson();
        System.out.println("Request body: " + gson.toJson(orders));
        //factory.requestBodyConverter(OrderItem.class);
        Call<ResponseBody> call = apiInstance.testPost(orders);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() == null) {
                    System.out.println("Response body was null");
                    return;
                }
                try {
                    System.out.print("Response: ");
                    System.out.println(response.body().string());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void postOrders(List<OrderItem> orders, final OrderStatus orderStatusActivity) {
        System.out.println("Trying to post following orders to api:");
        for(OrderItem item: orders) {
            System.out.println("{");
            item.print();
            System.out.println("}");
        }

        Call<List<OrderItem>> call = apiInstance.postOrderItems(orders);
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                if(response.body() == null) {
                    System.out.println("postOrder() -> Error: Api Response list was null");
                    return;
                }
                if(response.body().size() == orders.size()) {
                    System.out.println("Error: Api Response list size does not match posted list");
                }

                for(OrderItem item: response.body()) {
                    if(item != null)
                        item.print();
                    else
                        System.out.println("Null item");
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
                System.out.println("Fetching order from database");

                MasterOrderList masterList = new MasterOrderList();
                //masterList.updateOrderStatusByList(list);

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

    public void kitchenOrderItemReady(OrderItemAdapter adapter, List<OrderItem> list, int index, OrderItem orderItem) {
        Call<OrderItem> call = apiInstance.postOrderItemReady(orderItem);
        call.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                list.remove(index);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {

            }
        });
    }
}
