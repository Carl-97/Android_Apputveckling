package se.miun.ordernow.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

// Background thread that updates local lists from database.
public class BackgroundApiFetcher {
    private static final int LOOP_DELAY_MS = 1000;
    private static Timer timer = null;
    private static ApiCommunicator apiCommunicator;

    public BackgroundApiFetcher() {
        if(timer == null) {
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
        apiCommunicator.updateMasterOrderList();
    }
}
