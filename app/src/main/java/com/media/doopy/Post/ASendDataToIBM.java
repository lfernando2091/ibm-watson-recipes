/*
 * Copyright Lyo(c) 2017
 *  Autorized Luis Fernando Hernández Méndez
 * signature
 * b56a75f4cd38f40f3a0f4ded26e3902fe8f172594c9d9fafe889a2f4f8f5145a
 */

package com.media.doopy.Post;

import android.os.AsyncTask;

import com.ibm.watson.developer_cloud.service.security.IamOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifiedImages;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyOptions;
import com.media.doopy.Variables.Credentials;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;

public class ASendDataToIBM extends AsyncTask<String, Void, ClassifiedImages> {
    private PostListener listener;
    private VisualRecognition service;

    public ASendDataToIBM(PostListener listener){
        this.listener = listener;
        IamOptions options = new IamOptions.Builder().apiKey(Credentials.VISUAL_RECOGNITION_API_KEY).build();
        service = new VisualRecognition(Credentials.VISUAL_RECOGNITION_VERSION_DATE, options);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ClassifiedImages doInBackground(String... voids) {
        try {
            ClassifyOptions classifyOptions = new ClassifyOptions.Builder()
                    .imagesFile( new FileInputStream(voids[0]))
                    .imagesFilename(new File(voids[0]).getName())
                    .acceptLanguage(ClassifyOptions.AcceptLanguage.ES)
                    .threshold((float) 0.6)
                    .owners(Collections.singletonList("IBM"))
                    .build();
            return service.classify(classifyOptions).execute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    protected void onPostExecute(ClassifiedImages result) {
        super.onPostExecute(result);
        this.listener.onDataReceived(String.valueOf(result));
    }
}