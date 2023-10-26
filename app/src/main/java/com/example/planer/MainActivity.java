package com.example.planer;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

    private ActivityResultLauncher<Intent> launcher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // перезапуск если возвращаемся к списку задач
//                    Intent intent = getIntent();
//                    finish();
//                    startActivity(intent);
                    updateListScreen(getApplicationContext());
                    // Обработка результата активности здесь
//                    if (result.getResultCode() == RESULT_OK) {
//                        // Результат успешный
//                        Intent data = result.getData();
//
//                    } else {
//                        // Результат неудачный
//                    }
                });

        Context context = getApplicationContext();
        Objects.requireNonNull(getSupportActionBar()).hide();

        TabHost tabHost = findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tabSpec;

// Создание вкладки "Не срочно"
        tabSpec = tabHost.newTabSpec("Tab1");
        tabSpec.setIndicator("<img>");
        tabSpec.setContent(R.id.startTab);
        tabHost.addTab(tabSpec);

// Создание вкладки "Срочно"
        tabSpec = tabHost.newTabSpec("Tab2");
        tabSpec.setIndicator("Задачи");
        tabSpec.setContent(R.id.taskTab);
        tabHost.addTab(tabSpec);

// Создание вкладки "Очень срочно"
        tabSpec = tabHost.newTabSpec("Tab3");
        tabSpec.setIndicator("Заметки");
        tabSpec.setContent(R.id.noteTab);
        tabHost.addTab(tabSpec);

// Установка первой вкладки активной
        tabHost.setCurrentTab(1);

        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> {
            // Получаем активный таб
            @SuppressLint("CutPasteId") TabHost tabHost1 = findViewById(android.R.id.tabhost);
            int currentTab = tabHost1.getCurrentTab();

            // Открываем новое окно (Activity)
            Intent intent = new Intent(MainActivity.this, ActivityAddMaster.class);

// Передаем информацию об активном табе
            if (currentTab == 1) {
                intent.putExtra("text", "таск");
            } else {
                intent.putExtra("text", "ноте");
            }

            launcher.launch(intent);
        });

//        try {
//            TableExcel.test();
//        } catch (IOException e) {
//            System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n"+e+"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n");
//            throw new RuntimeException(e);
//        }

        updateListScreen(getApplicationContext());
        // Вызов AsyncTask для выполнения метода test() асинхронно
//        TableExcelAsyncTask asyncTask = new TableExcelAsyncTask();
//        asyncTask.execute();
    }
    public void updateListScreen(Context context){
        LinearLayout linearLayoutTasks = findViewById(R.id.linearLayoutTask);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) LinearLayout linearLayoutNotes = findViewById(R.id.linearLayoutNote);
        linearLayoutTasks.removeAllViews();
        linearLayoutNotes.removeAllViews();
        List<ObjectParams> listActiveItems = JsonFile.getValues(context, "active");
        assert listActiveItems != null;
        for (ObjectParams item: listActiveItems){
            LinearLayout objectType;
            if (Objects.equals(item.getDate(), null) || Objects.equals(item.getTime(), null)){
                objectType = linearLayoutNotes;
            } else {
                objectType = linearLayoutTasks;
            }
            assert objectType != null;
            setObjectInInterface(item, objectType);
        }
    }
    public void setObjectInInterface(ObjectParams objectParams, LinearLayout thisLinearLayout) {

        LayoutInflater inflater = LayoutInflater.from(this);
        View scrollViewView = inflater.inflate(R.layout.item_scroll_view, (ViewGroup) thisLinearLayout, false);

        // Модифицируем содержимое scrollViewView
        @SuppressLint({"CutPasteId", "MissingInflatedId", "LocalSuppress"}) TextView textViewTitle = scrollViewView.findViewById(R.id.labelTitle);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewDescription = scrollViewView.findViewById(R.id.labelDescription);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textViewDate = scrollViewView.findViewById(R.id.labelDateTime);

        textViewTitle.setText(objectParams.getTitle());
        textViewDescription.setText(objectParams.getContent());
        textViewDate.setText(objectParams.getDate());

        TableRow row2 = scrollViewView.findViewById(R.id.row2);
//        int targetHeight = 100; // Здесь можно установить целевую высоту строки
        row2.getLayoutParams().height = 0; // устанавливаем начальную высоту строки равной 0
        row2.requestLayout();
//        @SuppressLint("Recycle") ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight); // создаем аниматор для изменения высоты



        ImageButton button = scrollViewView.findViewById(R.id.button);
        @SuppressLint("CutPasteId") TextView row21 = scrollViewView.findViewById(R.id.labelDescription);
        button.setOnClickListener(v -> {
            if (row21.getHeight() == 0) {
                // Устанавливаем временно максимальную высоту строки
                row21.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                row21.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                int targetHeight = row21.getMeasuredHeight(); // Получаем фактическую высоту строки
                row21.getLayoutParams().height = 0; // Устанавливаем обратно высоту строки в 0

                ValueAnimator animator = ValueAnimator.ofInt(0, targetHeight);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(animation -> {
                    int value = (int) animation.getAnimatedValue();
                    row21.getLayoutParams().height = value;
                    row21.requestLayout();
                });
                animator.start();
            } else {
                int targetHeight = 0;
                ValueAnimator animator = ValueAnimator.ofInt(row21.getHeight(), targetHeight);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addUpdateListener(animation -> {
                    int value = (int) animation.getAnimatedValue();
                    row21.getLayoutParams().height = value;
                    row21.requestLayout();
                });
                animator.start();
            }
        });
        // Добавляем загруженную разметку в LinearLayout
        thisLinearLayout.addView(scrollViewView);
    }
}