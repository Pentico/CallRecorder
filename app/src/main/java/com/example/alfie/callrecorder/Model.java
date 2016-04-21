package com.example.alfie.callrecorder;

import android.util.Log;

/**
 * Created by Alfie on 2016/04/14.
 */
public class Model implements Comparable<Model> {

    private String callName;
    private String userNameFromContact;

    public String getUserNameFromContact(){
        return userNameFromContact;
    }

    public void setUserNameFromContact(String userNameFromContact){
        this.userNameFromContact = userNameFromContact;
    }

    public String getCallName(){
        return callName;
    }
    public Model(String callName) {
        this.callName = callName;
    }

    public void setCallName(String callName){
        this.callName = callName;
    }




    @Override
    public int compareTo(Model another) {

        Long date = Long.valueOf(this.getCallName().substring(1,15));
        Long date1 = Long.valueOf(another.getCallName().substring(1,15));

        return ( date1>date ? -1 :(date1==date ? 0 : 1));
    }
}
