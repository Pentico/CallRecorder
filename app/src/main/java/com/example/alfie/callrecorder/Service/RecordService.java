package com.example.alfie.callrecorder.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.IBinder;
import android.util.Log;

import com.example.alfie.callrecorder.Constants;

/**
 * Created by Alfie on 2016/04/16.
 */
public class RecordService extends Service {

    private MediaRecorder recorder = null;
    private String phoneNumber = null;

    private String fileName;
    private boolean onCall = false;
    private boolean recording = false;
    private boolean silentMode = false;
    private boolean onForeground = false;


    @Override
    public IBinder onBind(Intent intent){return null;}

    @Override
    public void onCreate(){super.onCreate();}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){

        Log.d(Constants.TAG,"RecordService onStartCommand");
        if (intent != null){
            //TODO : Left it here..........

        }
    }

}
