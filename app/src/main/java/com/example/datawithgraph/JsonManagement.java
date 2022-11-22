package com.example.datawithgraph;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;




public class JsonManagement {

    //Function gets app path, File name and data, saves into new file in mobile storage.
 public static void storeData(String path, String name, JSONObject data) throws Exception {

     File file = new File(path, name);
     System.out.println(path);


     FileOutputStream outputStream = new FileOutputStream(file);

     try {
         outputStream = new FileOutputStream((file));

         // Create new file in path
         if (!file.exists()) {
             file.createNewFile();
         }

         // get the content in bytes
         byte[] contentInBytes = data.toString().getBytes();

         //Write byte content to file and close file
         outputStream.write(contentInBytes);
         outputStream.flush();
         outputStream.close();
     } catch (IOException e) {
         e.printStackTrace();
     } finally {
         try {
             if (outputStream != null) {
                 outputStream.close();
             }
         } catch (IOException e) {
             e.printStackTrace();
         }
 }
 }



}
