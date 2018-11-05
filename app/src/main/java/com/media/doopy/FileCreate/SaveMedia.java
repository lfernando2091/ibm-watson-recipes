package com.media.doopy.FileCreate;

import android.os.Environment;

import java.io.File;

public class SaveMedia {
    public static final String LOCATION_SYNC_PARTS= File.separator+"Doopy"+File.separator+"backup"+File.separator;
    public static final String LOCATION_SYNC= File.separator+"sdcard"+File.separator+"Doopy"+File.separator+"backup"+File.separator;

    /** Create a File for saving something */
    public static File getOutputFile(String nameFile){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(LOCATION_SYNC);
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.
        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()) if (! mediaStorageDir.mkdirs()) return null;
        // Create a media file name
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + nameFile);
        if(mediaFile.exists())mediaFile.delete();
        return mediaFile;
    }

    public static File make(String nameFile){
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),LOCATION_SYNC_PARTS);
        if (! mediaStorageDir.exists()) if (! mediaStorageDir.mkdirs()) return null;
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator + nameFile);
        if(mediaFile.exists())mediaFile.delete();
        return mediaFile;
    }



    public static File getFile(String nameFile){
        return new File(LOCATION_SYNC+nameFile);
    }
}
