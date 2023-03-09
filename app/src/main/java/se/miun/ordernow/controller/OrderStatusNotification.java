package se.miun.ordernow.controller;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.ArrayList;
import java.util.List;

import se.miun.ordernow.R;
import se.miun.ordernow.model.OrderItem;
import se.miun.ordernow.view.FloorActivity;
import se.miun.ordernow.view.OrderStatus;
import se.miun.ordernow.view.TableChoiceActivity;

public class OrderStatusNotification {
    private int notificationId;
    private Context context;
    private List<OrderItem> updatedItems;

    private static String notificationChannelId;

    private static int idCounter = 0;

    public OrderStatusNotification(Context context) {
        if(context == null) {
            System.out.println("Context given was null");
        }
        this.context = context;
        notificationId = idCounter;
        ++idCounter;

        updatedItems = new ArrayList<>();

        if(notificationChannelId == null) {
            createNotificationChannel();
        }
    }

    private void createNotificationChannel() {
        notificationChannelId = "channel_1";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Order Channel";
            String description = "Channel for order notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(notificationChannelId, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void add(OrderItem item) {
        updatedItems.add(item);
    }

    public void execute() {
        if(updatedItems.size() == 0)
            return;

        while(updatedItems.size() != 0) {
            List<OrderItem> ordersByTable = new ArrayList<>();
            for(int i = 0; i < updatedItems.size(); ++i) {
                OrderItem item = updatedItems.get(i);
                if(ordersByTable.size() == 0) {
                    ordersByTable.add(item);
                    updatedItems.remove(i);
                    --i;
                    continue;
                }

                if(item.getTableNumber() == ordersByTable.get(0).getTableNumber()) {
                    ordersByTable.add(item);
                    updatedItems.remove(i);
                    --i;
                }
            }
            if(ordersByTable.size() == 0)
                return;

            pushNotification(ordersByTable);
            notificationId = idCounter;
            ++idCounter;
        }
    }

    private void pushNotification(List<OrderItem> items) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addNextIntent(new Intent(context, MainActivity.class));
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        Intent floorActivityIntent = new Intent(context, FloorActivity.class);
        Intent tableChooseActivityIntent = new Intent(context, TableChoiceActivity.class);
        Intent primaryIntent = new Intent(context, OrderStatus.class);
        primaryIntent.putExtra("tableNumber", items.get(0).getTableNumber());
        //stackBuilder.addNextIntent(primaryIntent);

        PendingIntent pendingIntent = PendingIntent.getActivities(context, 0,
                new Intent[]{mainActivityIntent, floorActivityIntent, tableChooseActivityIntent, primaryIntent}, PendingIntent.FLAG_ONE_SHOT);//stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        String title = "Table " + items.get(0).getTableNumber();
        String content = "";
        for(int i = 0; i < items.size(); ++i) {
            if(i != 0)
                content += ", ";

            content += items.get(i).getName();
        }
        content += " is ready!";

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, notificationChannelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(content)
                        .setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("No permission to post Notification!");
            return;
        }
        notificationManager.notify(notificationId, builder.build());
    }
}
