import java.util.List;
import java.util.Set;
import java.util.ArrayList;

/**
 * Interface simplificada para as operações do sistema.
 * Encapsula o acesso ao repositório de dados e fornece métodos para gerenciar usuários,sessões, amigos e recados.
 */

public class Facade{

    private Repositorio repositorio;

    /**
     * Inicializa o repositório.
     */
    public Facade() {
        this.repositorio = new Repositorio();
        this.repositorio.carregarDadosComunidades();
        this.repositorio.carregarDados();
    }

    /**
     * Cria um novo usuário no sistema.
     * @param login Nome de login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     */
    public void criarUsuario(String login, String senha, String nome) {

        repositorio.CriarUsuario(login, senha, nome);
    }

    /**
     * Abre uma sessão para um usuário.
     * @param login Nome de login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão.
     */
    public String abrirSessao(String login, String senha){
        return repositorio.abrirSessao(login, senha);
    }

    /**
     * Retorna um atributo específico de um usuário.
     * @param login Nome de login do usuário.
     * @param atributo Nome do atributo desejado.
     * @return Valor do atributo.
     */
    public String getAtributoUsuario(String login, String atributo){
        return repositorio.getAtributoUsuario(login, atributo);
    }


    /**
     * Reseta o sistema, apagando todos os dados.
     */
    public void zerarSistema(){
        repositorio.zerarSistema();
    }

    /**
     * Encerra o sistema, salvando os dados.
     */
    public void encerrarSistema(){
        repositorio.encerrarSistema();
    }

    /**
     * Finaliza a execução do sistema.
     */
    public void quit(){
        repositorio.quit();
    }

    /**
     * Edita o perfil de um usuário.
     * @param id ID do usuário.
     * @param atributo Atributo a ser alterado.
     * @param valor Novo valor do atributo.
     */
    public void editarPerfil(String id, String atributo, String valor){

        repositorio.editarPerfil(id, atributo, valor);
    }

    /**
     * Adiciona um amigo a lista de amigos do usuário.
     * @param id ID do usuário.
     * @param amigo ID do amigo a ser adicionado.
     */
    public void adicionarAmigo(String id, String amigo){
        repositorio.adicionarAmigo(id,amigo);
    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Nome de login do usuário.
     * @param amigo Nome do amigo.
     * @return true se forem amigos, false caso contrário.
     */
    public boolean ehAmigo(String login, String amigo){
        return repositorio.ehAmigo(login,amigo);
    }

    /**
     * Retorna a lista de amigos de um usuário.
     * @param login Nome de login do usuário.
     * @return Lista formatada de amigos.
     */
    public String getAmigos(String login){
        return repositorio.getAmigos(login);
    }

    /**
     * Envia um recado para um usuário.
     * @param id ID do remetente.
     * @param destinatario ID do destinatário.
     * @param mensagem Conteúdo do recado.
     */
    public void enviarRecado(String id,String destinatario, String mensagem){
        repositorio.enviarRecado(id, destinatario, mensagem);

    }


    /**
     * Lê um recado recebido por um usuário.
     * @param id ID do usuário.
     * @return Mensagem do recado.
     */
    public String lerRecado(String id){
        return repositorio.lerRecado(id);
    }

    public void criarComunidade(String sessao, String nome, String descrição){
        repositorio.criarComunidade(sessao,nome, descrição);
    }

    public String getDescricaoComunidade(String nome){
        return repositorio.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome){
        return repositorio.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome){
        return repositorio.getMembrosComunidade(nome);
    }

    public void adicionarComunidade(String id, String nome){

        repositorio.adicionarComunidade(id, nome);
    }

    public String getComunidades(String login){
        return repositorio.getComunidades(login);
    }

    public void enviarMensagem(String id,String nome, String mensagem){
        repositorio.enviarMensagem(id, nome, mensagem);

    }

    public String lerMensagem(String id){
        return repositorio.lerMensagem(id);
    }

    public void adicionarIdolo(String id, String idolo){
        repositorio.adicionarIdolo(id, idolo);
    }

    public String getFas(String login){
        return repositorio.getFas(login);
    }

    public boolean ehFa (String login, String idolo){
        return repositorio.ehFa(login,idolo);
    }

    public void adicionarPaquera(String id, String paquera){
        repositorio.adicionarPaquera(id, paquera);
    }

    public boolean ehPaquera(String id, String paquera){
        return repositorio.ehPaquera(id, paquera);
    }

    public String getPaqueras(String id){
        return repositorio.getPaqueras(id);
    }

    public void adicionarInimigo(String id, String inimigo){
        repositorio.adicionarInimigo(id, inimigo);
    }

    public void removerUsuario(String id){
        repositorio.removerUsuario(id);
    }
}

