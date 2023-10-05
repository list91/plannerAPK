package com.example.planer;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AppCompatActivity {
    public String priorityHard = "очень срочно";
    public String priorityMedium = "срочно";
    public String priorityEasy = "не срочно";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Context context = getApplicationContext();
        Objects.requireNonNull(getSupportActionBar()).hide();

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

// Создание вкладки "Не срочно"
        tabSpec = tabHost.newTabSpec("Tab1");
        tabSpec.setIndicator("Не срочно");
        tabSpec.setContent(R.id.blueBlock);
        tabHost.addTab(tabSpec);

// Создание вкладки "Срочно"
        tabSpec = tabHost.newTabSpec("Tab2");
        tabSpec.setIndicator("Срочно");
        tabSpec.setContent(R.id.greenBlock);
        tabHost.addTab(tabSpec);

// Создание вкладки "Очень срочно"
        tabSpec = tabHost.newTabSpec("Tab3");
        tabSpec.setIndicator("Очень срочно");
        tabSpec.setContent(R.id.yellowBlock);
        tabHost.addTab(tabSpec);

// Установка первой вкладки активной
        tabHost.setCurrentTab(0);

//        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView errorTextView = findViewById(R.id.infoLabel); // Идентификатор текстового поля

        JsonFile.createDefaultOutJsonFile(getApplicationContext());

        ObjectParams objectParams = new ObjectParams("23.12.23", "23:33", "помыть посуду", "помой посуду хорошо", "без категории", priorityHard, null);
        Object newObject = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams(null, null, "помыть посуду", "помой посуду хорошо", "без категории", priorityMedium, null);
        Object newObject2 = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams("23.12.23", "23:33", "помыть посуду", "помой посуду хорошо", "без категории", priorityEasy, null);
        Object newObject3 = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams(null, null, "помыть посуду", "помой посуду хорошо", "без категории", priorityEasy, null);
        Object newObject4 = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams("23.12.23", "23:33", "помыть посуду", "помой посуду хорошо", "без категории", priorityMedium, null);
        Object newObject5 = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams(null, null, "помыть посуду", "помой посуду хорошо", "без категории", priorityEasy, null);
        Object newObject6 = new Object(getApplicationContext(), objectParams);

        objectParams = new ObjectParams(null, null, "помыть посуду", "помой посуду хорошо", "без категории", priorityEasy, null);
        Object newObject7 = new Object(getApplicationContext(), objectParams);

        try {
            newObject.writeObject();
        newObject2.writeObject();
        newObject3.writeObject();
        newObject4.writeObject();
        newObject5.writeObject();
        newObject6.writeObject();
        newObject7.writeObject();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        LinearLayout linearLayoutNESROCHNO = findViewById(R.id.linearLayoutBlue);
        LinearLayout linearLayoutSROCHNO = findViewById(R.id.linearLayoutGreen);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linearLayoutVERYSROCHNO = findViewById(R.id.linearLayoutYellow);

//        setObjectInInterface("ЗАДАЧА_НОМЕР_1", linearLayoutNESROCHNO);
//        setObjectInInterface("ЗАДАЧА_НОМЕР_2", linearLayoutSROCHNO);
//        setObjectInInterface("ЗАДАЧА_НОМЕР_3", linearLayoutVERYSROCHNO);
//        setObjectInInterface("ЗАДАЧА_НОМЕР_4", linearLayoutNESROCHNO);

//        JSONObject values = JsonFile.getValue(context,"active", "notes" ,"1");
//        System.out.println(values);
        List<ObjectParams> listActiveItems = JsonFile.getValues(getApplicationContext(), "active");
        assert listActiveItems != null;
        for (ObjectParams item: listActiveItems){
            LinearLayout priority = null;
            if (Objects.equals(item.getPriority(), priorityEasy)){
                priority = linearLayoutNESROCHNO;
            } else if (Objects.equals(item.getPriority(), priorityMedium)) {
                priority = linearLayoutSROCHNO;
            } else if (Objects.equals(item.getPriority(), priorityHard)) {
                priority = linearLayoutVERYSROCHNO;
            }
            assert priority != null;
            setObjectInInterface(item, priority);
        }



//        for (JSONObject value: values){

//        }
//        System.out.println("########### "+values);
//        errorTextView.setText((CharSequence) value);

//        JsonFileOut.changeValueByKey(getApplicationContext(),"tasks", "testValue");
    }
    public void setObjectInInterface(ObjectParams objectParams, LinearLayout thisLinearLayout) {
        // Получаем доступ к корневому LinearLayout в activity_main.xml
//        LinearLayout linearLayout = findViewById(R.id.linearLayoutGreen);

        // Создаем LayoutInflater для загрузки разметки из item_scroll_view.xml
        LayoutInflater inflater = LayoutInflater.from(this);
        View scrollViewView = inflater.inflate(R.layout.item_scroll_view, (ViewGroup) thisLinearLayout, false);

        // Модифицируем содержимое scrollViewView
        @SuppressLint({"CutPasteId", "MissingInflatedId", "LocalSuppress"}) TextView textViewTitle = scrollViewView.findViewById(R.id.labelTitle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewDescription = scrollViewView.findViewById(R.id.labelDescription);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewDate = scrollViewView.findViewById(R.id.labelDateTime);
//        @SuppressLint("CutPasteId") TextView textViewTime = scrollViewView.findViewById(R.id.timeLabel);

        textViewTitle.setText(objectParams.getTitle());
//        textViewDescription.setText(objectParams.getContent());
        textViewDate.setText(objectParams.getDate());
//        textViewTime.setText(objectParams.getTime());

        TableRow row2 = scrollViewView.findViewById(R.id.row2);
        int targetHeight = 100; // Здесь можно установить целевую высоту строки
        row2.getLayoutParams().height = 0; // устанавливаем начальную высоту строки равной 0
        row2.requestLayout();
        ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight); // создаем аниматор для изменения высоты
        animator.setDuration(3000); // задаем продолжительность анимации
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue(); // получаем текущее значение анимации
                row2.getLayoutParams().height = value; // устанавливаем новую высоту строки
                row2.requestLayout();
            }
        });

// Установка продолжительности анимации
//        animation.setDuration(3000); // Здесь можно установить желаемую продолжительность анимации

// Запуск анимации по нажатию на кнопку
        ImageButton button = scrollViewView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row2.setVisibility(View.VISIBLE); // Показывает вторую строку перед запуском анимации
                animator.start(); // Запускает анимацию изменения высоты строки
            }
        });

//        textView.setText(txt);

        // Добавляем загруженную разметку в LinearLayout
        thisLinearLayout.addView(scrollViewView);
    }
}