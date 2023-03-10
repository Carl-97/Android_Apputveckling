package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.model.Table;
import se.miun.ordernow.view.TableChoiceActivity;

public class TableList {
    private static List<Table> tableList;

    public static final int DEFAULT_TABLE_COUNT = 8;
    public static final int DEFAULT_TABLE_SIZE = 4;

    public TableList() {
        if(tableList == null) {
            tableList = new ArrayList<>();

            ApiCommunicator apiCommunicator = new ApiCommunicator();
            apiCommunicator.fillTableList();
        }
    }

    public void addTables(List<Table> list) {
        tableList.addAll(list);

        TableChoiceActivity.updateAdapter();
    }

    public void addTables(int tableCount, int tableSize) {
        for(int i = 1; i <= tableCount; ++i) {
            tableList.add(new Table(i, tableSize));
        }
        TableChoiceActivity.updateAdapter();
    }

    public Table getTable(int tableIndex) {
        return tableList.get(tableIndex);
    }

    public int size() {
        return tableList.size();
    }
}
