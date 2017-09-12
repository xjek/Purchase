package ru.groupstp.procurementcommission.ui.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.groupstp.procurementcommission.R;
import ru.groupstp.procurementcommission.model.db.UserDB;
import ru.groupstp.procurementcommission.ui.activity.nomenclature.NomenclatureActivity;

import static ru.groupstp.procurementcommission.app.Common.plural;

/**
 * Сообщение с сервера о том, что имеются не проголосованные позиции
 */
public class MessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String message = remoteMessage.getData().get("message");
        String userEmail = UserDB.getAuth().getUserEmail();
        try {
            JSONArray messageArray = new JSONArray(message);
            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject object = messageArray.getJSONObject(i);

                if (object.getString("email").equals(userEmail)) {
                    sendNotification(Integer.valueOf(object.getString("qidList")));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendNotification(int count) {

        String message = createMessage(count);
        Intent intent = new Intent(this, NomenclatureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_laun)
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
    }

    private String createMessage(int count) {
        String text = plural(count, "заявка", "заявки", "заявок");
        return "В списке " + count + " " + text;
    }
}
