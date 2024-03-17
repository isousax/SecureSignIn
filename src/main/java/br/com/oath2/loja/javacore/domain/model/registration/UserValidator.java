package br.com.oath2.loja.javacore.domain.model.registration;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.security.InvalidParameterException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class UserValidator {
    static boolean isUsernameAvailable(String userNameToCheck, Sheet sheet) {
        for (int i = 0; i <= Registration.lastRow; i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    String usernameInCell = cell.getStringCellValue();
                    if (userNameToCheck.equals(usernameInCell)) {
                        throw new InvalidParameterException("Nome de usuário não disponível.");
                    }
                }
            }
        }
        return true;
    }

    public static boolean isUserNameValid(String userName) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\-._]*$");
        Matcher matcher = pattern.matcher(userName);
        if (matcher.matches()) {
            return true;
        } else {
            throw new InvalidParameterException("Nome de usuário deve conter apenas os caracteres: ., -, _ e alfanumérico.");
        }
    }


    public static boolean isPwdValid(String pwd) {
        if (pwd.length() >= 6) {
            return true;
        } else {
            throw new InvalidParameterException("Sua senha deve ter no mínimo 6 caracteres.");
        }
    }
}
