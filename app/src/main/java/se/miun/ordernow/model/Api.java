package se.miun.ordernow.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://10.82.226.203:8080/Java_Apputveckling-1.0-SNAPSHOT/api/v1/";
    @GET("item")
    Call<List<MenuItem>> getItems();

    @POST("item/newItem")
    Call<Temp> postOrderItems(@Body Temp item);
}
