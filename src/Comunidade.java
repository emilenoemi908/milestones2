import java.util.ArrayList;
import java.io.Serializable;

/**
 * Representa uma comunidade dentro de uma rede social,
 * contendo informações como nome, criador, descrição e membros participantes.
 */
public class Comunidade implements Serializable{

    private static final long serialVersionUID = 1L;

    /** Nome da comunidade. */
    private String nomeComunidade;

    /** Login do usuário criador da comunidade. */
    private String loginCriador;

    /** Descrição da comunidade. */
    private String descricao;

    /** Lista de logins dos membros da comunidade. */
    private ArrayList<String> membros;

    /**
     * Construtor de comunidade.
     * @param nomeComunidade Nome da comunidade.
     * @param loginCriador   Login do usuário que criou a comunidade.
     * @param descricao      Descrição da comunidade.
     */
    public Comunidade(String nomeComunidade, String loginCriador, String descricao){

        this.nomeComunidade=nomeComunidade;
        this.loginCriador=loginCriador;
        this.descricao=descricao;
        this.membros= new ArrayList<>();
    }

    /**
     * Retorna o nome da comunidade.
     * @return o nome da comunidade.
     */
    public String getNomeComunidade(){
        return nomeComunidade;
    }

    /**
     * Retorna o login do criador da comunidade.
     * @return o login do criador.
     */
    public String getLoginCriador(){
        return loginCriador;
    }

    /**
     * Retorna a lista de membros da comunidade.
     * @return uma lista com os logins dos membros.
     */
    public ArrayList<String> getMembros(){
        return membros;
    }

    /**
     * Retorna a descrição da comunidade.
     * @return a descrição da comunidade.
     */
    public String getDescricao(){
        return descricao;
    }


}

