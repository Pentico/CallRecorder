package com.example.alfie.callrecorder.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.alfie.callrecorder.Constants;
import com.example.alfie.callrecorder.MainActivity;

/**
 * Created by Alfie on 2016/04/18.
 */
public class PhoneReceiver extends BroadcastReceiver {

    private String phoneNumber;

    @Override
    public void onReceive(Context context, Intent intent) {
        phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
        String extraState = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        Log.d(Constants.TAG, "MyPhoneReciever phoneNumber " + phoneNumber);

        if (MainActivity.updateExternalStorageState() == Constants.MEDIA_MOUNTED) {
            try {
                SharedPreferences settings = context.getSharedPreferences(
                        Constants.LISTEN_ENABLED, 0);
                boolean silent = settings.getBoolean("silentMode", true);
                if (extraState != null) {
                    if (extraState.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        Intent myIntent = new Intent(context,
                                RecordService.class);
                        myIntent.putExtra("commandType",
                                Constants.STATE_CALL_START);
                        context.startService(myIntent);
                    } else if (extraState
                            .equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        Intent myIntent = new Intent(context,
                                RecordService.class);
                        myIntent.putExtra("commandType",
                                Constants.STATE_CALL_END);
                        context.startService(myIntent);
                    } else if (extraState
                            .equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        if (phoneNumber == null)
                            phoneNumber = intent
                                    .getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
                        Intent myIntent = new Intent(context,
                                RecordService.class);
                        myIntent.putExtra("commandType",
                                Constants.STATE_INCOMING_NUMBER);
                        myIntent.putExtra("phoneNumber", phoneNumber);
                        myIntent.putExtra("silentMode", silent);
                        context.startService(myIntent);
                    }
                } else if (phoneNumber != null) {
                    Intent myIntent = new Intent(context, RecordService.class);
                    myIntent.putExtra("commandType",
                            Constants.STATE_INCOMING_NUMBER);
                    myIntent.putExtra("phoneNumber", phoneNumber);
                    myIntent.putExtra("silentMode", silent);
                    context.startService(myIntent);
                }
            } catch (Exception e) {
                Log.e(Constants.TAG, "Exception");
                e.printStackTrace();
            }
        }
    }

}
