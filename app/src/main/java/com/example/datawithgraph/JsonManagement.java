package com.example.datawithgraph;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;




public class JsonManagement {
 public static void test(String path, String name, JSONObject data) throws Exception {
     // Serialization

//     Gson gson = new Gson();
//     String json = gson.toJson(data);
//     System.out.println(json);
     // ==> json is {"value1":1,"value2":"abc"}


     File file = new File(path, name);
     System.out.println(path);
     FileOutputStream outputStream = new FileOutputStream(file);

     try {
         outputStream = new FileOutputStream((file));
         if (!file.exists()) {
             file.createNewFile();
         }

         // get the content in bytes
         byte[] contentInBytes = data.toString().getBytes();

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
