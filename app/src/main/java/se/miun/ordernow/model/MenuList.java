package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import se.miun.ordernow.view.OrderMenu;

public class MenuList {
    private List<MenuItem> appetizers = null;
    private List<MenuItem> mainDishes = null;
    private List<MenuItem> desserts = null;

    public MenuList() {
        if(appetizers == null) {
            appetizers = new ArrayList<>();
            mainDishes = new ArrayList<>();
            desserts = new ArrayList<>();

            // Make API call here?
            getMenuListFromAPI();
        }
    }

    public void refreshMenuList() {
        appetizers.clear();
        mainDishes.clear();
        desserts.clear();

        getMenuListFromAPI();
    }

    public List<MenuItem> getAppetizers() {
        return appetizers;
    }

    public List<MenuItem> getMainDishes() {
        return mainDishes;
    }

    public List<MenuItem> getDesserts() {
        return desserts;
    }

    private void getMenuListFromAPI() {
        Call<List<MenuItem>> call = RetrofitClient.getInstance().getMyApi().getItems();
        call.enqueue(new Callback<List<MenuItem>>() {
            @Override
            public void onResponse(Call<List<MenuItem>> call, Response<List<MenuItem>> response) {
                List<MenuItem> list = response.body();
                // Add each item to their respective list.
                for(MenuItem item: list) {
                    // Appetizers
                    MenuItem.Type category = item.getCategory();
                    switch (category) {
                        case FÖRRÄTT: {
                            appetizers.add(item);
                            break;
                        }
                        case VARMRÄTT: {
                            mainDishes.add(item);
                            break;
                        }
                        case EFTERÄTT: {
                            desserts.add(item);
                            break;
                        }
                        default: {
                            System.out.println("Unknown category: " + item.getCategory() + ", name: " + item.getName());
                        }
                    }
                }
                OrderMenu.updateAdapters();
                System.out.println("API CALL SUCCESS!");
            }

            @Override
            public void onFailure(Call<List<MenuItem>> call, Throwable t) {
                System.out.println("API CALL FAILURE!");
                System.out.println(t.getMessage());
            }
        });
    }
}
