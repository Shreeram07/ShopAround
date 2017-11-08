package com.eyeworx.shoparound;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

/**
 * Created by Shreerama on 11/5/2017.
 */


public class CacheHelper {

    //Writes data to the Cache file
    public void writeData(String result, Context context) throws IOException {
        ObjectOutput writeToCache = null;
        try {
            JSONObject jsonObject = new JSONObject(result);
            writeToCache = new ObjectOutputStream(new FileOutputStream(new File(context.getCacheDir()+"/"+"cachedFile.srl")));
            writeToCache.writeObject(jsonObject.toString());
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        finally {
            if(writeToCache != null)
                writeToCache.close();
        }
    }

        //reads JSON data from the cache folder
        public JSONObject readData(Context context) {
            ObjectInputStream readCache = null;
            JSONObject jsonObject = null;
            try {
                readCache = new ObjectInputStream(new FileInputStream(new File(context.getCacheDir()+"/"+"cachedFile.srl")));
                jsonObject = new JSONObject((String) readCache.readObject());
            } catch (IOException | ClassNotFoundException | JSONException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (readCache != null)
                        readCache.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return jsonObject;
        }

    //check if Cache directory for the package com/eyeworx exists or not...!
    public boolean isCacheAvailable(Context context) {
        File directory = context.getCacheDir();
        if (directory.exists() && (directory.listFiles().length) > 0)
            return true;
        else return false;
    }
    }

