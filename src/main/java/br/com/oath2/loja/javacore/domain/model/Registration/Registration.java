package br.com.oath2.loja.javacore.domain.model.Registration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Registration {
    private final int FILE_SPREADSHEET = 0;
    private final String salt = BCrypt.gensalt();

    public void createNewUser(String file, String userId, @NotNull String pwd) throws IOException {

        FileInputStream inputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(this.FILE_SPREADSHEET);

        Row currentRow;
        int lastRowNum = sheet.getLastRowNum();
        currentRow = sheet.createRow(lastRowNum + 1);


        addDataToTable(userId, encryptPwd(pwd), currentRow);

        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);

        outputStream.close();

        System.out.println("Sucess!");
    }

    private @NotNull String encryptPwd(String pwd){
        return BCrypt.hashpw(pwd, this.salt);
    }

    private void addDataToTable(String userId, String pwdHashed, @NotNull Row row) {
        String createdDate = String.valueOf(LocalDateTime.now());

        setCellValue(row, 0, userId);
        setCellValue(row, 1, pwdHashed);
        setCellValue(row, 2, this.salt);
        setCellValue(row, 3, createdDate);
        setCellValue(row, 4, createdDate);

    }

    private void setCellValue(@NotNull Row row, int cellIndex, String value) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
    }

}
