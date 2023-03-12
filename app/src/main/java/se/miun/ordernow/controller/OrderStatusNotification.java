package se.miun.ordernow.controller;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import se.miun.ordernow.view.OrderStatusActivity;
import se.miun.ordernow.view.TableChoiceActivity;

// Class for creating notifications for OrderItems that are ready to serve.
// Create an object, add however many items you want, then run execute.
// It will group the OrderItems by table, and create a notification for each group.
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

    // Creates the notification channel
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

    // Adds OrderItems that we want to create a notification for.
    public void add(OrderItem item) {
        updatedItems.add(item);
    }

    // Executes notifications with the list of updated items.
    // Groups the list of updated items by tableId
    // Then pushes each group to a notification.
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

    // Creates a notification that display what table,
    // and that the given items are ready to serve.
    private void pushNotification(List<OrderItem> items) {
        // Backstack for the next activity.
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        Intent floorActivityIntent = new Intent(context, FloorActivity.class);
        Intent tableChooseActivityIntent = new Intent(context, TableChoiceActivity.class);

        Intent primaryIntent = new Intent(context, OrderStatusActivity.class);
        primaryIntent.putExtra("tableNumber", items.get(0).getTableNumber());

        // This pendingIntent is attached to the notification, upon pressing the notification the application will switch to this intent.
        PendingIntent pendingIntent = PendingIntent.getActivities(context, notificationId,
                new Intent[]{mainActivityIntent, floorActivityIntent, tableChooseActivityIntent, primaryIntent}, PendingIntent.FLAG_ONE_SHOT);

        // Create the content for the notification to display.
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
