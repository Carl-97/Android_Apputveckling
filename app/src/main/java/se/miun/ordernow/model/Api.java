package se.miun.ordernow.model;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://10.82.226.87:8080/Java_Apputveckling-1.0-SNAPSHOT/api/v1/";
    @GET("items")
    Call<List<MenuItem>> getItems();

    @GET("orders")
    Call<List<OrderItem>> getOrders();

    @POST("orders")
    Call<List<OrderItem>> postOrderItems(@Body List<OrderItem> orderItems);

    @POST("orders")
    Call<ResponseBody> testPost(@Body List<OrderItem> orderItems);

    @POST("order/kitchen")
    Call<OrderItem> postOrderItemReady(@Body OrderItem orderItem);
}
