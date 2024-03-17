package br.com.oath2.loja.javacore.service;

import br.com.oath2.loja.javacore.domain.model.registration.Registration;
import br.com.oath2.loja.javacore.domain.model.updateregister.DeleteUserInfo;
import br.com.oath2.loja.javacore.domain.model.updateregister.UpdatePassword;

import java.io.IOException;

public final class RegisterController {
    private static final String fileName = "secrets.xlsx";
    private static final String basePath = "C:\\Users\\Administrador\\OneDrive\\Programação\\Java\\ProjetosPessoais\\LojaOnline\\Login\\SecureSignIn\\src\\main\\resources\\banco\\senha\\";
    private static final String file = basePath.concat(fileName);

    public static void includeNewCredential(String userName, String pwd) {
        Registration modelDb = new Registration(RegisterController.file);
        try {
            modelDb.createNewUser(userName, pwd);
        } catch (IOException e) {
            throw new InternalError("Ocoreu um erro ao realizar o cadastro. Tento novamente dentro de instantes.");
        }
    }

    public static void updatePwd(String userName, String pwdCurrent, String pwdNew) {
        UpdatePassword modelDb = new UpdatePassword(RegisterController.file);
        try {
            modelDb.updatePassword(userName, pwdCurrent, pwdNew);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String userName, String pwdCurrent){
        DeleteUserInfo modelDb = new DeleteUserInfo(RegisterController.file);
        try{
            modelDb.deleteUser(userName, pwdCurrent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
