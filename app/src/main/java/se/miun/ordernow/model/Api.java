package se.miun.ordernow.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

// Our API calls.
public interface Api {
    String IP = "10.82.228.147";
    String BASE_URL = "http://" + IP + ":8080/Java_Apputveckling-1.0-SNAPSHOT/api/v1/";

    // Fetches all MenuItems
    @GET("items")
    Call<List<MenuItem>> getItems();

    // Fetches all OrderItems
    @GET("orders")
    Call<List<OrderItem>> getOrders();

    // Fetches all Tables
    @GET("table")
    Call<List<Table>> getTables();

    // Posts OrderItems.
    @POST("orders")
    Call<List<OrderItem>> postOrderItems(@Body List<OrderItem> orderItems);

    // Posts a unique OrderItem id,
    // which the server will use to update its boolean that checks if an item has been handled by the kitchen.
    @POST("orders/kitchen/{ID}")
    Call<OrderItem> postOrderItemReady(@Path("ID") int ID);
}
