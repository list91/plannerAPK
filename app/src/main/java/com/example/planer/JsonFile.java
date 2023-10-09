package com.example.planer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class JsonFile {
    public static final String OUT_DIR_NAME = "data.json";
    public static void setAttr(JSONObject jsonObject) throws JSONException {
        jsonObject.put("tasks", new JSONObject());
        jsonObject.put("notes", new JSONObject());
    }
    public static void createDefaultOutJsonFile(Context context){
        JSONObject mainJsonGroup = new JSONObject();

        JSONObject activeJsonGroup = new JSONObject();
        JSONObject completedJsonGroup = new JSONObject();
        JSONObject cancelledJsonGroup = new JSONObject();
        try {
            setAttr(activeJsonGroup);
            setAttr(completedJsonGroup);
            setAttr(cancelledJsonGroup);

            mainJsonGroup.put("active", activeJsonGroup);
            mainJsonGroup.put("completed", completedJsonGroup);
            mainJsonGroup.put("cancelled", cancelledJsonGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mainJsonGroup.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void setValue(Context context, String branch, String key, String newValue) {

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }
            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);
            branchJsonGroup.put(key, newValue);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mainJsonGroup.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    public static int getLenObjects(Context context, String branch, String objectType){
        return JsonFile.getAllComponentsInBranchType(context, branch, objectType).size();

    }
    public static List<String> getAllComponentsInBranchType(Context context, String branch, String objectType){
        List<String> items = new ArrayList<>();
        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }

            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);

            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);
            for (Iterator<String> it = branchObjectJsonGroup.keys(); it.hasNext(); ) {
                String i = it.next();
                items.add(i);
//                System.out.println(i);
            }

//            if (branchObjectJsonGroup.has(taskId)){
//                JSONObject idGroup = branchObjectJsonGroup.getJSONObject(taskId);
//                idGroup.put(key, newValue);
//            } else {
//                branchObjectJsonGroup.put(taskId, newJsonAttr);
//            }

//            branchJsonGroup.put(objectType, branchObjectJsonGroup);
//            mainJsonGroup.put(branch, branchJsonGroup);

//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write(mainJsonGroup.toString());
//            fileWriter.flush();
//            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return items;
    }
    public static void addValue(Context context, String branch, String objectType, String key, String newValue, String taskId) {

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }

            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);

            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);

            JSONObject newJsonAttr = new JSONObject();
            newJsonAttr.put(key, newValue);


            if (branchObjectJsonGroup.has(taskId)){
                JSONObject idGroup = branchObjectJsonGroup.getJSONObject(taskId);
                idGroup.put(key, newValue);
            } else {
                branchObjectJsonGroup.put(taskId, newJsonAttr);
            }

            branchJsonGroup.put(objectType, branchObjectJsonGroup);
            mainJsonGroup.put(branch, branchJsonGroup);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mainJsonGroup.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    public static void addJsonGroup(Context context, String branch, String objectType, String key, JSONObject newValue, String taskId) {

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }

            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);

            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);

            JSONObject newJsonAttr = new JSONObject();
            newJsonAttr.put(key, newValue);


            if (branchObjectJsonGroup.has(taskId)){
                JSONObject idGroup = branchObjectJsonGroup.getJSONObject(taskId);
                idGroup.put(key, newValue);
            } else {
                branchObjectJsonGroup.put(taskId, newJsonAttr);
            }

            branchJsonGroup.put(objectType, branchObjectJsonGroup);
            mainJsonGroup.put(branch, branchJsonGroup);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(mainJsonGroup.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
    public static JSONObject getValue(Context context, String branch, String objectType, String key) {

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }

            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);

            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);
            System.out.println(branchJsonGroup);
            JSONObject notesObj = branchJsonGroup.getJSONObject("notes");

            for (Iterator<String> it = notesObj.keys(); it.hasNext(); ) {
                String thisKey = it.next();
                JSONObject noteObj = notesObj.getJSONObject(thisKey);
                String title = noteObj.getString("title");
                String content = noteObj.getString("content");
                String category = noteObj.getString("category");
                String priority = noteObj.getString("priority");

                System.out.println("Заметка " + thisKey + ":");
                System.out.println("Заголовок: " + title);
                System.out.println("Содержимое: " + content);
                System.out.println("Категория: " + category);
                System.out.println("Приоритет: " + priority);
                System.out.println();
            }
            return branchObjectJsonGroup;//.getJSONObject(key);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<ObjectParams> getValues(Context context, String branch) {

        try {
            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
            JSONObject mainJsonGroup = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
            }

            assert mainJsonGroup != null;
            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);

//            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);
//            System.out.println(branchJsonGroup);
            JSONObject tasksObjects = branchJsonGroup.getJSONObject("tasks");
            JSONObject notesObjects = branchJsonGroup.getJSONObject("notes");
            List<JSONObject> listBranch = Arrays.asList(tasksObjects, notesObjects);
            List<ObjectParams> eventList = new ArrayList<>();
            for(JSONObject thisBranch : listBranch){
                for (Iterator<String> it = thisBranch.keys(); it.hasNext(); ) {
                    String thisKey = it.next();
                    System.out.println(thisKey);
                    JSONObject thisObject = thisBranch.getJSONObject(thisKey);
                    String date = null;
                    String time = null;
                    if (thisObject.has("date_time")) {
                        JSONObject dateTime = thisObject.getJSONObject("date_time");
                        date = dateTime.getString("date");
                        time = dateTime.getString("time");
                    }
//                    if (thisObject.has("time")) {
//                        time = thisObject.getString("time");
//                    }
                    String title = thisObject.getString("title");
                    String content = thisObject.getString("content");
                    String category = thisObject.getString("category");
                    String priority = thisObject.getString("priority");
                    ObjectParams objectParams =new ObjectParams(date, time, title, content, category, priority, thisKey);
                    eventList.add(objectParams);
//                                        System.out.println("Заметка " + thisKey + ":");
//                    System.out.println("Заголовок: " + title);
//                    System.out.println("Содержимое: " + content);
//                    System.out.println("Категория: " + category);
                    System.out.println("~~~~~~~~~~~~~~: " + date);
                    System.out.println();
                    // TODO выводит список всех (в даннном случае АКТИВНЫХ) событий, в виде списка экземпляров Параметров
                }
            }
            return eventList;//.getJSONObject(key);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void main(String[] args) {
//        getValues()
//    }
//    public static JSONObject getValue(Context context, String branch, String objectType, String key) {
//
//        try {
//            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
//            JSONObject mainJsonGroup = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
//            }
//
//            assert mainJsonGroup != null;
//            JSONObject branchJsonGroup = mainJsonGroup.getJSONObject(branch);
//
//            JSONObject branchObjectJsonGroup = branchJsonGroup.getJSONObject(objectType);
//            System.out.println(branchJsonGroup);
//            JSONObject notesObj = branchJsonGroup.getJSONObject("notes");
//
//            for (Iterator<String> it = notesObj.keys(); it.hasNext(); ) {
//                String thisKey = it.next();
//                JSONObject noteObj = notesObj.getJSONObject(thisKey);
//                String title = noteObj.getString("title");
//                String content = noteObj.getString("content");
//                String category = noteObj.getString("category");
//                String priority = noteObj.getString("priority");
//
//                System.out.println("Заметка " + thisKey + ":");
//                System.out.println("Заголовок: " + title);
//                System.out.println("Содержимое: " + content);
//                System.out.println("Категория: " + category);
//                System.out.println("Приоритет: " + priority);
//                System.out.println();
//            }
//    public static JSONObject getBranchObject(Context context, String branchName) {
//
//        try {
//            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
//            JSONObject mainJsonGroup = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
//            }
//            assert mainJsonGroup != null;
//            return mainJsonGroup.getJSONObject(branchName);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static JSONObject getBranch(Context context, String branchName) {
//
//        try {
//            File file = new File(context.getExternalFilesDir(null), OUT_DIR_NAME);
//            JSONObject mainJsonGroup = null;
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                mainJsonGroup = new JSONObject(new String(Files.readAllBytes(file.toPath())));
//            }
//            assert mainJsonGroup != null;
//            return mainJsonGroup.getJSONObject(branchName);
//        } catch (JSONException | IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
}