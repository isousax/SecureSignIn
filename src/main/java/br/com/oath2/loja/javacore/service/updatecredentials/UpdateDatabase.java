package br.com.oath2.loja.javacore.service.updatecredentials;

import br.com.oath2.loja.javacore.domain.model.Registration.Registration;

import java.io.IOException;

public final class UpdateDatabase {
    private static final String fileName = "secrets.xlsx";
    private static final String basePath = "C:\\Users\\Administrador\\OneDrive\\Programação\\Java\\ProjetosPessoais\\LojaOnline\\Login\\SecureSignIn\\src\\main\\resources\\banco\\senha\\";
    private static final String file = basePath.concat(fileName);

    public static void includeNewCredential(String userId, String pwd) {
        Registration modelDb = new Registration();
        try {
            modelDb.createNewUser(UpdateDatabase.file, userId, pwd);
        } catch (IOException e) {
            throw new InternalError("Ocoreu um erro ao realizar seu cadastro. Tento novamente dentro de instantes.");
        }

    }
}
