package com.example.planer;

import android.content.Context;

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
    public void writeObject(){
        String branch = "active";
        String id = Integer.toString(JsonFile.getLenObjects(context,branch,typeObject));
        if (Objects.equals(typeObject, "task")){
            JsonFile.addValue(context,branch,"date_time", typeObject, "title", title);
        }
//         "1";
        JsonFile.addValue(context,branch, typeObject, "title", title, id);
        JsonFile.addValue(context,branch, typeObject, "content", content, id);
        JsonFile.addValue(context,branch, typeObject, "category", category, id);
        JsonFile.addValue(context,branch, typeObject, "priority", priority, id);

    }
}
