package com.example.planer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Objects;

public class ActivityAddMaster extends AppCompatActivity {
    private Button timeButton;
    private Button dateButton;
    //    private final EditText date = findViewById(R.id.contentDate);
    private EditText hour;
    private EditText date;
    private EditText minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_master);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Создание (подставить задча или заметка)");

        Button buttonOk = findViewById(R.id.okButton);
        buttonOk.setOnClickListener(event -> {
            // Создается если есть ДАТА ; ВРЕМЯ ; ЗАГОЛОВОК ; ПРИОРИТЕТ
            System.out.println("######################################################");
            EditText title = findViewById(R.id.titleEditText);
//            EditText description = findViewById(R.id.contentEditText);
            EditText h = findViewById(R.id.contentHour);
            EditText m = findViewById(R.id.contentMinute);
            EditText d = findViewById(R.id.contentDate);

            // TODO когда нибудь сделай проверку на схожесть даты с определенной маской и чтобы дата была не ранее текущей
            if (title.getText().length() != 0 &&
                h.getText().length() != 0 &&
                d.getText().length() != 0 &&
                m.getText().length() != 0){
                // тут идет создание (пока тут не включен ПРИОРИТЕТ)
            }else{
                // тут вывод ошибки что чего то не хватает
            }

//            System.out.println(title.getText().length() + " title");
//            System.out.println(h.getText().length() + " h");
//            System.out.println(m.getText().length() + " m");
        });

        hour = findViewById(R.id.contentHour);
        minute = findViewById(R.id.contentMinute);
        date = findViewById(R.id.contentDate);

        // Получаем переданный текст
        Intent intent = getIntent();
        String text = intent.getStringExtra("text");

        // Отображаем текст на экране
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView textView = findViewById(R.id.title);
        textView.setText(text);


        timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(v -> showTimePickerDialog());

        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(v -> showDatePickerDialog());

    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);

        @SuppressLint("SetTextI18n") DatePickerDialog datePickerDialog = new DatePickerDialog(ActivityAddMaster.this,
                (view, year, month, dayOfMonth) -> {

                    date.setText(dayOfMonth+"."+(month+1)+"."+year);
                    // Действия при выборе даты
                    // year - год
                    // month - месяц (0-11)
                    // dayOfMonth - день месяца
                }, y, m, d);

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityAddMaster.this,
                android.R.style.Theme_Holo_Light_Dialog,
                (view, hourOfDay, min) -> {
                    hour.setText(String.valueOf(hourOfDay));
                    minute.setText(String.valueOf(min));
                }, 0, 0, true);

        timePickerDialog.getWindow().setLayout(900, 900);  // Установка размеров таймпикера (600 на 600)

        timePickerDialog.show();
    }
}