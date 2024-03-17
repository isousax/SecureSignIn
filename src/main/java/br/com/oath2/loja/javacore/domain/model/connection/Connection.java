package br.com.oath2.loja.javacore.domain.model.connection;

import br.com.oath2.loja.javacore.domain.model.registration.Registration;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Connection {
    protected static Workbook workbook;

    public static @NotNull Sheet openFile(String file) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(file)) {
            Connection.workbook = new XSSFWorkbook(inputStream);
            return workbook.getSheetAt(Registration.FILE_SPREADSHEET);
        }
    }

    public static void closeFile(String file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            Connection.workbook.write(outputStream);
        }
    }

}
