package se.miun.ordernow.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api {

    String IP = "192.168.1.162";
    String BASE_URL = "http://" + IP + ":8080/Java_Apputveckling-1.0-SNAPSHOT/api/v1/";
    @GET("items")
    Call<List<MenuItem>> getItems();

    @GET("orders")
    Call<List<OrderItem>> getOrders();

    @GET("table")
    Call<List<Table>> getTables();

    @POST("orders")
    Call<List<OrderItem>> postOrderItems(@Body List<OrderItem> orderItems);

    @POST("orders/kitchen/{ID}")
    Call<OrderItem> postOrderItemReady(@Path("ID") int ID);
}
