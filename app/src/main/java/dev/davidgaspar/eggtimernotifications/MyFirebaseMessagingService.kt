package dev.davidgaspar.eggtimernotifications;

import android.app.NotificationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage
import dev.davidgaspar.eggtimernotifications.util.sendNotification

class MyFirebaseMessagingService : FirebaseMessagingService()  {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d(TAG, "From: ${message.from}")

        if (message.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${message.data}")
        }

        message.notification?.let { notification ->
            if (notification.body != null) {
                Log.d(TAG, "Message Notification Body: ${notification.body}")
                sendNotification(notification.body!!)
            } else {
                Log.w(TAG, "Message Notification Body is null")
            }
        }
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private fun sendNotification(messageBody: String) {
        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        )

        notificationManager?.sendNotification(messageBody, applicationContext)
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        sendRegistrationToServer(token)
    }

    private fun sendRegistrationToServer(token: String) {

    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}
