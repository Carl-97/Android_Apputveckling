package se.miun.ordernow.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public void postOrders(List<OrderItem> orders, final OrderStatus orderStatusActivity) {
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

                MasterOrderList masterList = new MasterOrderList();
                masterList.updateOrderStatusByList(list);
            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                System.out.println("API Response Error: Fetching orders from api");
                System.out.println(t.getMessage());
            }
        });
    }
}
