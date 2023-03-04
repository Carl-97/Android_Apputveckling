package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundApiFetcher {
    private static final int LOOP_DELAY_MS = 5000;
    private static Timer timer = null;
    private static MasterOrderList masterOrderList;
    private static ApiCommunicator apiCommunicator;

    public BackgroundApiFetcher() {
        if(timer == null) {
            masterOrderList = new MasterOrderList();
            apiCommunicator = new ApiCommunicator();

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    getOrderListAndUpdate();
                }
            }, 0, LOOP_DELAY_MS);
        }
    }

    private void getOrderListAndUpdate() {
        List<OrderItem> orderList = new ArrayList<>();
        apiCommunicator.updateMasterOrderList();
    }
}
