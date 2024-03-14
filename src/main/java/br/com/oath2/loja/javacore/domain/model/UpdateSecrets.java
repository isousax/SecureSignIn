package br.com.oath2.loja.javacore.domain.model;

public interface UpdateSecrets {
    void UpdatePwd(String file);
    void deleteUser(String file);
    void validateCredentials();

}
