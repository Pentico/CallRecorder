package com.example.alfie.callrecorder.Helper;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.text.format.DateFormat;
import android.util.Log;

import com.example.alfie.callrecorder.Constants;
import com.example.alfie.callrecorder.Model;

/**
 * Created by Alfie on 2016/04/13.
 */
public class FileHelper {

    /**
     * returns absolute fiel directory
     *
     */
    public static String getFilename(String phoneNumber) throws Exception{

        String filepath = null;
        String myDate = null;
        File file = null;

        if(phoneNumber == null) {
            throw new Exception("Phone number can't be empty");

        }
        try{

            //TODO : do the path function
            filepath = getFilePath();

            file = new File(filepath, Constants.FILE_DIRECTORY);  // TODO :class file to be created

            if(!file.exists()){
                file.mkdir();
            }

            myDate = (String)DateFormat.format("yyyyMMddkkmmss",new Date());

            //Clean characters in file name
            phoneNumber = phoneNumber.replaceAll("[\\*\\+-]","");
            if (phoneNumber.length() > 10){
                phoneNumber.substring(phoneNumber.length()-10,phoneNumber.length());
                //TODO substring ???
            }

        }
        catch (Exception e){
            Log.e(Constants.TAG, "Exception" +phoneNumber);
            e.printStackTrace();
        }
        return (file.getAbsolutePath() + "/d" + myDate + "p" + phoneNumber + "3gp");
    }

    /**
     * Returns path of file
     * @return
     */
    //TODO:  Learn were it is called
    public static String getFilePath(){

        //TODO: Change to user seletected directory
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * Delete All records
     */
    public static void deleteAllRecords(Activity caller){

        String filepath = getFilePath() + "/" + Constants.FILE_DIRECTORY;
        File file = new File(filepath);

        String listOfFileNames[] = file.list();

        for(int i= 0; i < listOfFileNames.length; i++){

            File file1 = new File(filepath, listOfFileNames[i]);

            if(file1.exists()){
                file1.delete();
            }
        }

        filepath = caller.getFilesDir().getAbsolutePath() + "/"
                + Constants.FILE_DIRECTORY;
        file = new File(filepath);

        String listOfFileNames2[] = file.list();

        for (int i = 0; i < listOfFileNames2.length; i++) {
            File file2 = new File(filepath, listOfFileNames2[i]);
            if (file2.exists()) {
                file2.delete();
            }
        }

    }

    /**
     * Obtains the contact list for the currently selected account.
     *
     * @return A cursor for accessing the contact list.
     */
    public static String getContactName(String phoneNum, Activity caller){

        String res = phoneNum;
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER};

        String selection = null;
        String[] selectionArgs = null;

        //cursor for query the list
        Cursor names = caller.getContentResolver().query(uri,
                projection,selection,selectionArgs,null);

        int indexName = names.getColumnIndex
                (ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = names.getColumnIndex
                (ContactsContract.CommonDataKinds.Phone.NUMBER);

        if(names.getCount() > 0){

            names.moveToFirst();
            do{

                String name = names.getString(indexName);
                String number = names.getString(indexNumber)
                        .replaceAll("[\\*\\+-]", "");

                if (number.compareTo(phoneNum) == 0){
                    res = name;
                    break;
                }
            }while (names.moveToNext());
        }
        return res; //why am i return the res ???
    }

    /**
     * Fetches a list of previous recordings
     * @param file
     * @param caller
     * @return
     */
    public static List<Model>listDir2(File file, Activity caller ){

        File[] files = file.listFiles();
        List<Model> fileList = new ArrayList<Model>();
        for (File file1 : files){

            if (!file.getName().matches(Constants.FILE_NAME_PATTERN)){

                Log.d(Constants.TAG, String.format(
                        "'%s' didn't match the file name pattern",
                        file.getName()));
                continue;
            }

            Model model = new Model (file1.getName());
            String phoneNum = model.getCallName().substring(16,
                    model.getCallName().length()-4);
            model.setUserNameFromContact(getContactName(phoneNum,caller));
            fileList.add(model);
        }

        Collections.sort(fileList);
        Collections.sort(fileList,Collections.reverseOrder()); // ???

        return fileList;


    }

    //Works with the function above
    public static List<Model> listFiles(Activity caller){

        String filepath = FileHelper.getFilePath();
        final File file = new File(filepath,Constants.FILE_DIRECTORY);

        if(!file.exists()){
            file.mkdirs();
        }

        final  List<Model> listDir = FileHelper.listDir2(file,caller);

        filepath = caller.getFilesDir().getAbsolutePath();
        final File file1 = new File(filepath, Constants.FILE_DIRECTORY);

        if(!file1.exists()){
            file1.mkdirs();
        }

        final List<Model> listDir2 = FileHelper.listDir2(file1,caller);

        listDir.addAll(listDir2);

        return listDir;
    }

    //delete a File
    public static void deleteFile(String fileName){

        if(fileName == null){
            return;
        }

        Log.d(Constants.TAG, "FileHelper deleteFile" + fileName);

        try{
            File file = new File(fileName);

            if (file.exists()){
                file.delete();
            }

        }catch (Exception e){

            Log.e(Constants.TAG, "Exception");
            e.printStackTrace();
        }
    }
}
