package com.example.planer;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Objects;

public class ActivityAddMaster extends AppCompatActivity {
    private Button timeButton;
    private Button dateButton;
    //    private final EditText date = findViewById(R.id.contentDate);
    private EditText hour;
    private EditText date;
    private EditText minute;
    private boolean isTask;
    private MainActivity mainActivity;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_master);



        Button buttonOk = findViewById(R.id.okButton);
        buttonOk.setOnClickListener(event -> {
            // Создается если есть ДАТА ; ВРЕМЯ ; ЗАГОЛОВОК ; ПРИОРИТЕТ
            EditText title = findViewById(R.id.titleEditText);
            EditText content = findViewById(R.id.contentEditText);
//            EditText description = findViewById(R.id.contentEditText);
            @SuppressLint("CutPasteId") EditText h = findViewById(R.id.contentHour);
            @SuppressLint("CutPasteId") EditText m = findViewById(R.id.contentMinute);
            @SuppressLint("CutPasteId") EditText d = findViewById(R.id.contentDate);


            // TODO когда нибудь сделай проверку на схожесть даты с определенной маской и чтобы дата была не ранее текущей
            if (title.getText().length() != 0){
                String time;
                String date;
                if(h.getText().length() != 0 && d.getText().length() != 0 && m.getText().length() != 0 && isTask){

                    date = d.getText().toString();
                    time = h.getText().toString()+":"+m.getText().toString();
                } else {
                    date = null;
                    time = null;
                }
                // тут идет создание
                String ttle = title.getText().toString();
                Spinner spin = findViewById(R.id.prioritySpinner);
                String selectedItem = spin.getSelectedItem().toString();
                System.out.println("######################################################");
//                System.out.println(defaultSelection);

                ObjectParams objectParams = new ObjectParams(date, time, ttle, content.getText().toString(), "no category", selectedItem, null);
                Object newObject = new Object(this, objectParams);
                try {
                    newObject.writeObject();
                    closeActivity();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


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

        CheckBox checkBoxTask = findViewById(R.id.is_task);
        checkBoxTask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isTask = isChecked;
            if (!isTask) {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Новая заметка");
                setEnabled(false);
            } else {
                Objects.requireNonNull(getSupportActionBar()).setTitle("Новая задача");
                setEnabled(true);
            }
        });
        isTask = checkBoxTask.isActivated();


        timeButton = findViewById(R.id.timeButton);
        timeButton.setOnClickListener(v -> showTimePickerDialog());

        dateButton = findViewById(R.id.dateButton);
        dateButton.setOnClickListener(v -> showDatePickerDialog());

        if (!isTask) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Новая заметка");
            setEnabled(false);
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Новая задача");
            setEnabled(true);
        }
    }
    public void setEnabled(boolean flag){
        dateButton.setEnabled(flag);
        timeButton.setEnabled(flag);
        date.setEnabled(flag);
        hour.setEnabled(flag);
        minute.setEnabled(flag);
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
    private void closeActivity() {
        finish();
    }

}