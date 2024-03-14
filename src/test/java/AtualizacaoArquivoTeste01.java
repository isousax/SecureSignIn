import br.com.oath2.loja.javacore.service.updatecredentials.UpdateDatabase;

public class AtualizacaoArquivoTeste01 {
    public static void main(String[] args) {
        String user = "user1";
        String pwd = "1234";
        UpdateDatabase.includeNewCredential(user, pwd);
    }
}
