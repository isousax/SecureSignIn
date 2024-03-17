package br.com.oath2.loja.javacore.domain.model.updateregister;

import br.com.oath2.loja.javacore.domain.exceptions.IncorrectPasswordException;
import br.com.oath2.loja.javacore.domain.exceptions.UserNotFoundException;
import br.com.oath2.loja.javacore.domain.model.connection.Connection;
import br.com.oath2.loja.javacore.domain.model.registration.UserValidator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public class UpdatePassword implements UpdateSecrets {
    private final String file;

    public UpdatePassword(String file) {
        this.file = file;
    }

    @Override
    public void updatePassword(String userName, String pwdCurrent, String pwdNew) throws IOException {
        Sheet sheet = Connection.openFile(this.file);

        try {
            UserValidator.isPwdValid(pwdNew);
            Row row = getUserData(sheet, userName);
            boolean isCredentialsValid = validateCredentials(row, pwdCurrent);
            boolean isPasswordMatchCurrent = isPasswordMatchCurrent(row, pwdCurrent, pwdNew);

            if (isCredentialsValid) {
                if (!isPasswordMatchCurrent) {
                    insertNewPwdInTable(row, pwdNew);
                } else {
                    throw new IncorrectPasswordException("The new password must be different from the current password.");
                }
            } else {
                throw new IncorrectPasswordException("Password Invalid");
            }

        } finally {
            Connection.closeFile(this.file);
        }
    }

    @Override
    public Row getUserData(Sheet sheet, String userName) {
        int lastRow = sheet.getLastRowNum();
        Row row = null;
        boolean userFound = false;

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

    @Override
    public boolean validateCredentials(Row row, String pwdCurrent) {
        Cell cell = row.getCell(1);
        String currentPwdHashed = cell.getStringCellValue();

        return BCrypt.checkpw(pwdCurrent, currentPwdHashed);
    }

    private boolean isPasswordMatchCurrent(Row row, String pwdCurrent, String pwdNew) {
        Cell pwdCell = row.getCell(1);
        Cell saltCell = row.getCell(2);

        String currentPwdHashed = pwdCell.getStringCellValue();
        String salt = saltCell.getStringCellValue();

        String pwdNewHashed = BCrypt.hashpw(pwdNew, salt);

        return currentPwdHashed.equals(pwdNewHashed);
    }

    private void insertNewPwdInTable(Row row, String pwdNew) {
        Cell cell = row.getCell(1);
        final String salt = BCrypt.gensalt();
        cell.setCellValue(BCrypt.hashpw(pwdNew, salt));

        cell = row.getCell(2);
        cell.setCellValue(salt);

        System.out.println("Password changed successfully!");
    }


    @Override
    public void deleteUser(String userName, String pwdCurrent) {
    }
}
