package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.OrderMenuActivity;

// MenuList is the list of MenuItems that we display in our order menu.
public class MenuList {
    private static List<MenuItem> appetizers = null;
    private static List<MenuItem> mainDishes = null;
    private static List<MenuItem> desserts = null;

    public MenuList() {
        if(appetizers == null) {
            initMenuList();
        }
    }

    // On initialization we ask the api for the current menu offered.
    private void initMenuList() {
        appetizers = new ArrayList<>();
        mainDishes = new ArrayList<>();
        desserts = new ArrayList<>();

        ApiCommunicator apiCommunicator = new ApiCommunicator();
        apiCommunicator.fillMenuList();
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

    // Adds MenuItem to the correct list.
    public void addMenuList(List<MenuItem> menuList) {
        for(MenuItem item: menuList) {
            MenuItem.Type type = item.getCategory();

            switch(type) {
                case APPETIZER:
                    appetizers.add(item);
                    break;
                case MAINDISH:
                    mainDishes.add(item);
                    break;
                case DESSERT:
                    desserts.add(item);
                    break;
                default:
            }
        }

        // If ordermenu is open we must update adapters.
        OrderMenuActivity.updateAdapters();
    }
}
