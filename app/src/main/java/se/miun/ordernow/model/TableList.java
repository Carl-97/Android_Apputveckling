package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.model.Table;

public class TableList {
    private static List<Table> tableList;

    public TableList() {
        if(tableList == null) {
            tableList = new ArrayList<>();

            for(int i = 1; i <= 8; ++i) {
                tableList.add(new Table(i, 4));
            }
        }
    }

    public Table getTable(int tableIndex) {
        return tableList.get(tableIndex);
    }
}
