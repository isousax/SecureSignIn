import br.com.oath2.loja.javacore.service.RegisterController;

public class AtualizacaoArquivoTeste01 {
    public static void main(String[] args) {
        String user = "userTest1";
        String pwd = "1234567";
        String pwdNew ="12345678";

        RegisterController.includeNewCredential(user, pwd);

        //RegisterController.updatePwd(user, pwd, pwdNew);

        //RegisterController.deleteUser(user, pwd);
    }
}