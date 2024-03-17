package br.com.oath2.loja.javacore.domain.model.updateregister;

import br.com.oath2.loja.javacore.domain.exceptions.IncorrectPasswordException;
import br.com.oath2.loja.javacore.domain.exceptions.UserNotFoundException;
import br.com.oath2.loja.javacore.domain.model.connection.Connection;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class DeleteUserInfo implements UpdateSecrets {
    private final String file;

    public DeleteUserInfo(String file) {
        this.file = file;
    }

    @Override
    public void deleteUser(String userName, String pwdCurrent) throws IOException {
        Sheet sheet = Connection.openFile(this.file);

        try {
            Row row = getUserData(sheet, userName);
            boolean isCredentialsValid = validateCredentials(row, pwdCurrent);

            if (isCredentialsValid) {
                removeUserFromTable(sheet, row);
            } else {
                throw new IncorrectPasswordException("Password Invalid");
            }

        } finally {
            Connection.closeFile(this.file);
        }
    }

    @Override
    public boolean validateCredentials(Row row, String pwdCurrent) {
        Cell cell = row.getCell(1);
        String currentPwdHashed = cell.getStringCellValue();

        return BCrypt.checkpw(pwdCurrent, currentPwdHashed);
    }

    @Override
    public Row getUserData(Sheet sheet, String userName) {
        boolean userFound = false;
        int lastRow = sheet.getLastRowNum();
        Row row = null;

        for (int i = 1; i <= lastRow; i++) {
            row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            String usernameInCell = cell.getStringCellValue();

            if (userName.equals(usernameInCell)) {

                userFound = true;
                break;

            }

        }

        if (userFound) {
            return row;
        } else {
            throw new UserNotFoundException("Usuario não encontrado.");
        }
    }

    private void removeUserFromTable(Sheet sheet, Row row) {
        int rowIndex = row.getRowNum();
        int lastRowNum = sheet.getLastRowNum();
        sheet.removeRow(row);

        if (rowIndex != lastRowNum) sheet.shiftRows(rowIndex + 1, lastRowNum, -1);

        System.out.println("User deleted successful!");
    }

    @Override
    public void updatePassword(String userName, String pwdCurrent, String pwdNew) throws IOException {
    }
}
