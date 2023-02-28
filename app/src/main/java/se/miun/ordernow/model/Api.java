package se.miun.ordernow.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    String BASE_URL = "http://10.82.226.203:8080/Java_Apputveckling-1.0-SNAPSHOT/api/v1/";
    @GET("item")
    Call<List<Temp>> getItems();
}
