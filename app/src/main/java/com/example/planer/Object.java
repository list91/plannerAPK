package com.example.planer;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Object {
    private final String date;
    private final String time;
    private final String title;
    private final String content;
    private final String category;
    private final String priority;
    private final String typeObject;
    private final Context context;

    public Object(Context context, ObjectParams objectParams){
        date = objectParams.getDate();
        time = objectParams.getTime();
        title = objectParams.getTitle();
        content = objectParams.getContent();
        category = objectParams.getCategory();
        priority = objectParams.getPriority();
        this.context = context;
        if (time == null || date == null){
            typeObject = "notes";
        } else {
            typeObject = "tasks";
        }
    }
    public void writeObject() throws JSONException {
        String branch = "active";
        String id = Integer.toString(JsonFile.getLenObjects(context,branch,typeObject));
        if (Objects.equals(typeObject, "tasks")){
//            JsonFile.addValue(context,branch,"date_time", typeObject, "title", title);
            JSONObject dataTime = new JSONObject();
            dataTime.put("date", date);
            dataTime.put("time", time);
            JsonFile.addJsonGroup(context,branch, typeObject, "date_time", dataTime, id);
//            JsonFile.addValue(context,branch, typeObject, "date_time", date, id);
        }
//         "1";
        JsonFile.addValue(context,branch, typeObject, "title", title, id);
        JsonFile.addValue(context,branch, typeObject, "content", content, id);
        JsonFile.addValue(context,branch, typeObject, "category", category, id);
        JsonFile.addValue(context,branch, typeObject, "priority", priority, id);

    }
}
