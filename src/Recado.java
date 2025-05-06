import java.io.Serializable;

/**
 * Representa um recado enviado entre usuários do sistema.
 */

public class Recado implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Mensagem do recado */
    private String mensagem;

    /** Login do remetente do recado */
    private String loginRemetente;

    /**
     * Construtor do recado.
     * @param mensagem Conteúdo da mensagem.
     * @param loginRemetente Login do remetente.
     */
    public Recado(String mensagem, String loginRemetente) {
        this.mensagem = mensagem;
        this.loginRemetente = loginRemetente;
    }

    /**
     * Retorna a mensagem do recado.
     * @return Conteúdo da mensagem.
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Retorna o login do remetente da mensagem.
     * @return Login do remetente.
     */
    public String getLoginRemetente() {
        return loginRemetente;
    }

}