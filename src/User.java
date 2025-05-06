import java.io.Serializable;
import java.util.Map;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;
/**
 * Representa um usuário do sistema.
 * Cada usuário possui login, senha, nome e pode interagir com outros usuários através de amizades e mensagens.
 */

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Login do usuário. */
    private String login;

    /** Nome do usuário. */
    private String nome;

    /** Senha do usuário. */
    private String senha;

    /** Identificação da sessão do usuário. */
    private String idSessao;

    /** Atributos extras do usuário, armazenados em um mapa. */
    private Map<String, Atributo> atributosExtras;

    /** Lista de amigos do usuário. */
    private ArrayList<String> amigos = new ArrayList<>();

    /** Solicitações de amizade recebidas pelo usuário. */
    private Set<String> solicitacoesAmizade = new HashSet<>();

    /** Lista de mensagens recebidas pelo usuário. */
    private ArrayList<Recado> recados= new ArrayList<>();

    /**  Lista com os nomes das comunidades que o usuário participa. */
    private List<String> comunidades=new ArrayList<>();

    /** Lista com as mensagens recebidas pelo usuário em comunidades. */
    private ArrayList<String> mensagensComunidades= new ArrayList<>();

    /** Lista com os logins dos usuários que são fãs deste usuário. */
    private ArrayList<String> fas= new ArrayList<>();

    /** Lista com os logins dos usuários que este usuário considera ídolos. */
    private ArrayList<String> idolos= new ArrayList<>();

    /** Lista com os logins dos usuários que o usuário adicionou como paqueras. */
    private ArrayList<String> paqueras= new ArrayList<>();

    /** Lista com os logins dos usuários que este usuário marcou como inimigos. */
    private ArrayList<String> inimigos= new ArrayList<>();


    /**
     * Construtor da classe User.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     */

    public User(String login, String senha, String nome) {
        this.login = login;
        this.nome = nome;
        this.senha = senha;
        this.idSessao = null;
        this.atributosExtras = new HashMap<>();
        this.amigos = new ArrayList<>();
        this.solicitacoesAmizade=new HashSet<>();
        this.recados= new ArrayList<>();
        this.comunidades=new ArrayList<>();
        this.mensagensComunidades= new ArrayList<>();
        this.fas= new ArrayList<>();
        this.idolos=new ArrayList<>();
        this.paqueras= new ArrayList<>();
        this.inimigos=new ArrayList<>();
    }

    /**
     * Pega o nome de login do usuário.
     * @return Nome login do usuário.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Define o nome login do usuário.
     * @param login Novo nome de login do usuário.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Pega o nome do usuário.
     * @return Nome do usuário.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do usuário.
     * @param nome Novo nome do usuário.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Pega a senha do usuário.
     * @return A senha do usuário.
     */
    public String getSenha() {
        return senha;
    }

    /**
     * Define a senha do usuário.
     * @param senha Nova senha do usuário.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Pega o ID da sessão do usuário.
     * @return O ID da sessão.
     */
    public String getIdSessao() {
        return idSessao;
    }

    /**
     * Define o ID da sessão do usuário.
     * @param idSessao Novo ID da sessão.
     */
    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    /**
     * Adiciona um atributo extra ao usuário.
     * @param chave Nome do atributo.
     * @param valor Valor do atributo.
     */
    public void addAtributoExtra(String chave, String valor) {

        atributosExtras.put(chave, new Atributo(chave, valor));
    }


    /**
     * Obtém um atributo extra do usuário.
     * @param chave Nome do atributo.
     * @return O atributo correspondente.
     */

    public Atributo getAtributoExtra(String chave) {
        Atributo atributo = atributosExtras.get(chave);

        return atributo;
    }

    /**
     * Pega a lista de amigos do usuário.
     * @return Lista de amigos.
     */
    public ArrayList<String> getAmigos() {

        return amigos;
    }

    /**
     * Obtém a lista de solicitações de amizade recebidas pelo usuário.
     * @return Lista de solicitações de amizade.
     */
    public Set<String> getSolicitacoesAmizade() {

        return solicitacoesAmizade;
    }

    /**
     * Obtém a lista de mensagens recebidas pelo usuário.
     * @return Lista de mensagens.
     */
    public ArrayList<Recado> getRecados(){
        return recados;
    }

    /**
     * Obtém a lista de comunidades às quais o usuário pertence.
     * @return uma lista com os nomes das comunidades.
     */
    public  List<String> getComunidades(){
        return comunidades;
    }

    /**
     * Adiciona uma nova comunidade à lista de comunidades do usuário.
     * @param nome Nome da comunidade a ser adicionada.
     */
    public void adicionarComunidade(String nome){
        comunidades.add(nome);
    }

    /**
     * Retorna a lista de mensagens recebidas pelo os usuários das comunidades.
     * @return uma lista de mensagens de comunidades.
     */
    public ArrayList<String> getMensagensComunidades(){
        return mensagensComunidades;
    }

   /**
    * Retorna a lista de usuários  ídolos do usuário atual.
    * @return uma lista como os nomes dos ídolos.
    */
    public ArrayList<String> getIdolos() {
        return idolos;
    }

    /**
     * Retorna a lista de usuários que são fãs do usuário atual.
     * @return uma lista de nomes dos fãs.
     */
    public ArrayList<String> getFas() {
        return fas;
    }

    /**
     * Retorna a lista de usuários que o usuário atual considera como paqueras.
     * @return uma lista de nomes dos paqueras.
     */
    public ArrayList<String> getPaqueras(){
        return paqueras;
    }

    /**
     * Retorna a lista de usuários considerados inimigos pelo usuário atual.
     * @return uma lista com os nomes dos inimigos.
     */
    public ArrayList<String> getInimigos(){
        return inimigos;
    }


}