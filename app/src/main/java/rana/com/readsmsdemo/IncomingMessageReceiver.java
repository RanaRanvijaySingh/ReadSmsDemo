package rana.com.readsmsdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class IncomingMessageReceiver extends BroadcastReceiver {

    private final String TAG = "IncomingMessageReceiver";
    private static MainActivity mContext;

    public IncomingMessageReceiver() {

    }

    public void setActivityContext(MainActivity mContext) {
        IncomingMessageReceiver.mContext = mContext;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String message = currentMessage.getDisplayMessageBody();
                    Log.i(TAG, "senderNum: " + phoneNumber + "\nmessage: " + message);
                    Toast.makeText(context, "senderNum : " + phoneNumber + " message: " + message, Toast.LENGTH_LONG).show();
                    notificationReceivedWithData(phoneNumber, getActivationCode(message).trim());
                }
            }
        } catch (Exception exception) {
            Log.e("SmsReceiver", "Exception smsReceiver: " + exception);
        }
    }

    private String getActivationCode(String message) {
        String[] messageContents = message.split(":");
        Log.i("Broadcast receiver", "activation code is : " + messageContents[1]);
        return messageContents[1];
    }

    private void notificationReceivedWithData(String phoneNumber, String message) {
        mContext.notificationReceivedDataIs(phoneNumber, message);
    }
}
