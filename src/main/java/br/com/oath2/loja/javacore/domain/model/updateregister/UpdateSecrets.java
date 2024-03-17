package br.com.oath2.loja.javacore.domain.model.updateregister;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.IOException;

public interface UpdateSecrets {
    void deleteUser(String userName, String pwdCurrent) throws IOException;

    void updatePassword(String userName, String pwdCurrent, String pwdNew) throws IOException;

    Row getUserData(Sheet sheet, String userName);
    boolean validateCredentials(Row row, String pwdCurrent);
}
