import java.io.Serializable;

public class Recado implements Serializable {

    private static final long serialVersionUID = 1L;
    private String mensagem;
    private String loginRemetente;

    public Recado(String mensagem, String loginDestinatario) {
        this.mensagem = mensagem;
        this.loginRemetente = loginDestinatario;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getLoginRemetente() {
        return loginRemetente;
    }

}