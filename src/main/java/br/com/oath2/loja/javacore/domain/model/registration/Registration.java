package br.com.oath2.loja.javacore.domain.model.registration;

import br.com.oath2.loja.javacore.domain.model.connection.Connection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jetbrains.annotations.NotNull;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.time.LocalDateTime;

public class Registration {
    private final String file;
    public static final int FILE_SPREADSHEET = 0;
    private final String salt = BCrypt.gensalt();
    protected static int lastRow;

    public Registration(String file) {
        this.file = file;
    }

    public void createNewUser(@NotNull String userName, @NotNull String pwd) throws IOException {
        Sheet sheet = Connection.openFile(this.file);
        Registration.lastRow = sheet.getLastRowNum();

        if (UserValidator.isUserNameValid(userName) &&
                UserValidator.isPwdValid(pwd) &&
                UserValidator.isUsernameAvailable(userName, sheet)) {

            Row currentRow = sheet.createRow(Registration.lastRow + 1);

            addDataToTable(userName, encryptPwd(pwd), currentRow);

            Connection.closeFile(this.file);

            System.out.println("Credentials added successfully!");
        } else {
            Connection.closeFile(this.file);
        }
    }

    private @NotNull String encryptPwd(String pwd) {
        return BCrypt.hashpw(pwd, this.salt);
    }

    private void addDataToTable(String userId, String pwdHashed, @NotNull Row row) {
        String getCurrentDate = String.valueOf(LocalDateTime.now());

        setCellValue(row, 0, userId);
        setCellValue(row, 1, pwdHashed);
        setCellValue(row, 2, this.salt);
        setCellValue(row, 3, getCurrentDate);
        setCellValue(row, 4, getCurrentDate);

    }

    private void setCellValue(@NotNull Row row, int cellIndex, String value) {
        Cell cell = row.createCell(cellIndex);
        cell.setCellValue(value);
    }
}
