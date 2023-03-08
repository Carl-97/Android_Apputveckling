package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.view.OrderMenu;

public class MenuList {
    private static List<MenuItem> appetizers = null;
    private static List<MenuItem> mainDishes = null;
    private static List<MenuItem> desserts = null;

    public MenuList() {
        if(appetizers == null) {
            initMenuList();
        }
    }

    private void initMenuList() {
        appetizers = new ArrayList<>();
        mainDishes = new ArrayList<>();
        desserts = new ArrayList<>();

        ApiCommunicator apiCommunicator = new ApiCommunicator();
        apiCommunicator.fillMenuList();
    }

    public void refreshMenuList() {
        appetizers.clear();
        mainDishes.clear();
        desserts.clear();

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
                    System.out.println("tried to add unrecognized item type in menulist");
            }
        }

        // If ordermenu is open we must update adapters.
        OrderMenu.updateAdapters();
    }
}
