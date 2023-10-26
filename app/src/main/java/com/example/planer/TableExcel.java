package com.example.planer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TableExcel {
    public static void test() throws IOException {
        // Загрузка файла по ссылке
        URL url = new URL("https://old.altspu.ru/engine/download.php?id=36367");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        InputStream inputStream = connection.getInputStream();

// Создание книги Excel из потока
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Получение первого листа

// Перебор строк и столбцов для извлечения данных
        for (Row row : sheet) {
            for (Cell cell : row) {
                // Получение значения ячейки и обработка данных
                String cellValue = cell.getStringCellValue();
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@"+cellValue);
                // Ваш код для обработки значения ячейки
            }
        }
        // Закрытие ресурсов
        workbook.close();
        inputStream.close();
        connection.disconnect();
    }
}
