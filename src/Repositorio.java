import java.io.*;
import java.util.*;

/**
 * Repositorio: Gerencia(abre sessões, cria usuários,carrega e salva dados) usuários, suas sessões, amizades e envia/ler mensagens/recados.
 */
public class Repositorio {

    /** Mapa de usuários registrados no sistema. */
    private Map<String, User> users= new HashMap<>();

    /** Mapa de comunidades criadas no sistema. */
    private Map<String, Comunidade> comunidades = new HashMap<>();


    /**
     * Construtor do repositório.
     */
    public Repositorio(){
        this.users=new HashMap<>();
        this.comunidades= new HashMap<>();
    }

    /**
     * Quando existe "users.dat", carrega os dados.
     */
    public void carregarDados(){

        File arquivo= new File("users.dat");

        if(!arquivo.exists()){
            return;
        }

        try(ObjectInputStream lendo= new ObjectInputStream(new FileInputStream("users.dat"))){
            users=(Map<String, User>) lendo.readObject();
        }
        catch(IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }

    }

    /**
     * Quando existe "comunidades.dat", carrega os dados.
     */
    public void carregarDadosComunidades() {
        File arquivo = new File("comunidades.dat");
        if (!arquivo.exists()) {
            return;
        }

        try (ObjectInputStream lendo = new ObjectInputStream(new FileInputStream("comunidades.dat"))) {
            comunidades = (Map<String, Comunidade>) lendo.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar comunidades: " + e.getMessage());
        }
    }

    /**
     * Reseta o repositorio de usuários, removendo todos e deletando o arquivo de dados "users.dat".
     */

    public void zerarSistema() {

        users.clear();

        File arquivo = new File("users.dat");

        if (arquivo.exists()) {
            arquivo.delete();
        } else {
            System.out.println("O arquivo não existe.");
        }

        comunidades.clear();
        File arquivoComunidad = new File("comunidades.dat");
        if (arquivoComunidad.exists()) {
            arquivoComunidad.delete();
        } else {
            System.out.println("O arquivo de comunidades não existe.");
        }
    }

    /**
     * Salva os dados dos usuários no arquivo "users.dat".
     */
    public void encerrarSistema() {

        try(ObjectOutputStream writeUser= new ObjectOutputStream(new FileOutputStream("users.dat"))){

            writeUser.writeObject(users);

        }

        catch(IOException e){
            System.out.println("Erro ao salvar cadastro" + e.getMessage());
        }

        try (ObjectOutputStream writeComunidade = new ObjectOutputStream(new FileOutputStream("comunidades.dat"))) {
            writeComunidade.writeObject(comunidades);
        } catch (IOException e) {
            System.out.println("Erro ao salvar comunidades: " + e.getMessage());
        }
    }

    /**
     * Finaliza a execução do repositorio.
     */
    public void quit(){

        System.exit(0);
    }

    /**
     * Busca o usuário pelo Id da sessão.
     * @param id ID sessão do usuário.
     * @return User correspondente ao id ou null se não for encontrado.
     */
    private User getUserById(String id) {
        for (User user : users.values()) {
            if (id.equals(user.getIdSessao())) {
                return user;
            }
        }
        return null;
    }

    /**
     * Cria um novo usuário.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @param nome Nome do usuário.
     * @throws IllegalArgumentException Se os dados forem inválidos.
     */
    public void CriarUsuario(String login, String senha, String nome){

        if (login == null || login.trim().isEmpty() ) {
            throw new IllegalArgumentException("Login inválido.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha inválida.");
        }


        if(users.containsKey(login)){
            throw new IllegalArgumentException("Conta com esse nome já existe.");
        }

        User newUser= new User (login, senha, nome);
        users.put(login, newUser);

    }

    /**
     * Retorna um atributo específico do usuário.
     * @param login Nome do login do usuário.
     * @param atributo Nome do atributo que deve ser recuperado.
     * @return Valor do atributo solicitado.
     * @throws InvalidUserException Se o usuário não existir.
     * @throws InvalidAtributeException Se o atributo não estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {

        User user = users.get(login);

        if (user==null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        switch (atributo.toLowerCase()) {

            case "Login":
                return user.getLogin();
            case "nome":
                return user.getNome();
            case "Senha":
                return user.getSenha();
            default:

                Atributo atributoExtra = user.getAtributoExtra(atributo);

                if(atributoExtra == null){
                    throw new InvalidAtributeException("Atributo não preenchido.");
                }


                String nome = atributoExtra.getNome();
                String valor = atributoExtra.getValor();

                if (valor != null && !valor.trim().isEmpty()) {
                    return valor;
                }
                else {
                    throw new IllegalArgumentException("Atributo não preenchido.");
                }

        }
    }

    /**
     * Abre uma sessão para um usuário.
     * @param login Nome do login do usuário.
     * @param senha Senha do usuário.
     * @return ID da sessão gerado.
     * @throws IllegalArgumentException Se o login ou senha forem inválidos.
     */
    public String abrirSessao(String login, String senha) {



        if (login == null || senha == null || login.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        User user = users.get(login);

        if (user == null || !senha.equals(user.getSenha())) {
            throw new IllegalArgumentException("Login ou senha inválidos.");
        }

        if (user.getIdSessao() != null) {
            return user.getIdSessao();
        }

        String id = null;
        do {
            id = UUID.randomUUID().toString();
        } while (id == null || id.trim().isEmpty());

        user.setIdSessao(id);
        users.put(user.getLogin(), user);

        return id;

    }

    /**
     * Edita o perfil do usuário, alterando um atributo especifico (login, nome, senha) ou preenchendo um novo.
     * @param id ID sessão do usuário.
     * @param atributo Atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws InvalidUserException Se o usuário não for encontrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        User usuarioEncontrado = getUserById(id);


        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        switch (atributo.toLowerCase()) {
            case "nome":
                usuarioEncontrado.setNome(valor);
                break;
            case "senha":
                usuarioEncontrado.setSenha(valor);
                break;
            default:

                usuarioEncontrado.addAtributoExtra(atributo, valor);
                break;
        }

    }

    /**
     * Adiciona um amigo à lista de amigos do usuário.
     * @param id ID do usuário.
     * @param amigo Nome do amigo a ser adicionado.
     * @throws InvalidUserException Se o amigo não existir ou se já forem amigos.
     */
    public  void adicionarAmigo(String id, String amigo) {

        User userAmigo = users.get(amigo);

        if (userAmigo == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (amigo == null || amigo.trim().isEmpty()) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        User usuario = getUserById(id);


        if (usuario == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (!users.containsKey(amigo)) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if (usuario.getIdSessao().equals(userAmigo.getIdSessao())) {
            throw new InvalidUserException("Usuário não pode adicionar a si mesmo como amigo.");
        }

        if (usuario.getAmigos().contains(amigo)) {
            throw new InvalidUserException("Usuário já está adicionado como amigo.");
        }

        if(userAmigo.getInimigos().contains(usuario.getLogin())){
            throw new IllegalArgumentException("Função inválida: " + userAmigo.getNome() + " é seu inimigo.");
        }

        if (userAmigo.getSolicitacoesAmizade().contains(usuario.getLogin()) || usuario.getSolicitacoesAmizade().contains(userAmigo)) {


            usuario.getAmigos().add(userAmigo.getLogin());
            userAmigo.getAmigos().add(usuario.getLogin());


            usuario.getSolicitacoesAmizade().remove(amigo);
            userAmigo.getSolicitacoesAmizade().remove(usuario.getLogin());
        }


        if (usuario.getSolicitacoesAmizade().contains(amigo) || userAmigo.getSolicitacoesAmizade().contains(usuario.getNome())) {

            throw new IllegalArgumentException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        usuario.getSolicitacoesAmizade().add(userAmigo.getLogin());


    }

    /**
     * Verifica se dois usuários são amigos.
     * @param login Nome de login de um dos usuários.
     * @param amigo Nome de um dos usuários.
     * @return true se forem amigos, false caso contrário.
     */
    public boolean ehAmigo(String login, String amigo) {

        User user = users.get(login);
        User userAmigo=users.get(amigo);

        if (user== null || userAmigo==null) {
            return true;
        }

        if(user.getAmigos().contains(amigo) && userAmigo.getAmigos().contains(user)){
            return true;
        }
        if(user.getAmigos().contains(amigo) && !userAmigo.getAmigos().contains(user)){
            return true;
        }
        if(!user.getAmigos().contains(amigo) && userAmigo.getAmigos().contains(user)){
            return true;
        }

        return false;

    }

    /**
     * Exibi a lista de amigos do usuário.
     * @param login Nome de login do usuário.
     * @return Lista de amigos formatada como string.
     */
    public String getAmigos(String login) {

        User user= users.get(login);

        if(user!=null || user.getAmigos()!=null){

            List<String> amigos = new ArrayList<>(user.getAmigos());

            return amigos.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    /**
     * Envia recado/mensagem para outro usuário.
     * @param id ID sessão do usuário.
     * @param destinatario Nome do destinatário da mensagem.
     * @param mensagem Conteúdo do recado/mensagem.
     * @throws InvalidRecadoException Se houver erro no envio do recado.
     */
    public void enviarRecado (String id, String destinatario, String mensagem) throws InvalidRecadoException {

        if (id == null || id.trim().isEmpty() || destinatario == null || destinatario.trim().isEmpty()) {
            throw new InvalidRecadoException("ID ou destinatário inválido.");
        }

        User userDestinatario = users.get(destinatario);
        User user = getUserById(id);

        if (user == null || userDestinatario == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (id.equals(userDestinatario.getIdSessao())) {
            throw new InvalidUserException("Usuário não pode enviar recado para si mesmo.");
        }

        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new InvalidRecadoException("Mensagem inválida.");
        }
        if(userDestinatario.getInimigos().contains(user.getLogin())){
            throw new IllegalArgumentException("Função inválida: " + userDestinatario.getNome() + " é seu inimigo.");
        }


        Recado recado = new Recado(mensagem, user.getLogin());
        userDestinatario.getRecados().add(recado);
    }

    /**
     * Faz a leitura do recado mais antigo da lista de racdos do usuário.
     * @param id ID sessão do usuário.
     * @return Recado removido da lista de recados.
     * @throws InvalidRecadoException Se não houver recados.
     * @throws InvalidUserException Se o usuário não existir.
     * @throws IllegalArgumentException Se id for inválido.
     */
    public String lerRecado (String id) throws InvalidRecadoException {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID inválido.");
        }

        User usuarioEncontrado = getUserById(id);

        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (usuarioEncontrado.getRecados().isEmpty()) {
            throw new InvalidRecadoException("Não há recados.");
        }

        Recado recadoRemovido = usuarioEncontrado.getRecados().remove(0);

        return recadoRemovido.getMensagem();

    }

    /**
     * Cria uma nova comunidade com o nome e descrição fornecidos.
     * @param sessao ID da sessão do usuário criador.
     * @param nome Nome da nova comunidade.
     * @param descricao Descrição da comunidade.
     * @throws InvalidUserException se o usuário não estiver cadastrado.
     * @throws InvalidCommunityException se a comunidade já existir.
     */
    public void criarComunidade(String sessao, String nome, String descricao){

        User newUser=getUserById(sessao);

        if (newUser == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if(comunidades.containsKey(nome)){
            throw new InvalidCommunityException("Comunidade com esse nome já existe.");
        }

        Comunidade novaComunidade= new Comunidade(nome, newUser.getLogin(), descricao);
        comunidades.put(nome, novaComunidade);
        novaComunidade.getMembros().add(newUser.getLogin());

        newUser.getComunidades().add(novaComunidade.getNomeComunidade());

    }

    /**
     * Retorna a descrição de uma comunidade.
     * @param nome Nome da comunidade.
     * @return Descrição da comunidade.
     * @throws InvalidCommunityException se a comunidade não existir.
     */
    public String getDescricaoComunidade(String nome) {

        Comunidade nova = comunidades.get(nome);

        if (nova == null || !comunidades.containsKey(nova.getNomeComunidade())) {
            throw new InvalidCommunityException("Comunidade não existe.");
        }

        return nova.getDescricao();

    }

    /**
     * Retorna o login do criador de uma comunidade.
     * @param nome Nome da comunidade.
     * @return Login do criador.
     * @throws InvalidCommunityException se a comunidade não existir.
     */
    public String getDonoComunidade(String nome) {

        Comunidade nova = comunidades.get(nome);
        if (nova == null) {
            throw new InvalidCommunityException("Comunidade não existe.");
        }

        return nova.getLoginCriador();

    }

    /**
     * Retorna os membros de uma comunidade.
     * @param nome Nome da comunidade.
     * @return Lista com os logins dos membros.
     * @throws InvalidCommunityException se a comunidade não existir.
     */
    public String getMembrosComunidade(String nome){

        Comunidade nova = comunidades.get(nome);

        if (nova == null) {
            throw new InvalidCommunityException("Comunidade não existe.");
        }

        return "{" + String.join(",", nova.getMembros()) + "}";


    }

    /**
     * Adiciona um usuário a uma comunidade existente.
     * @param id ID do usuário.
     * @param nome Nome da comunidade.
     * @throws InvalidUserException se o usuário não estiver cadastrado.
     * @throws InvalidCommunityException se o usuário já for membro ou se a comunidade não existir.
     */
    public void adicionarComunidade(String id, String nome){

        User newUser= getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        Comunidade nova= comunidades.get(nome);

        if(newUser.getComunidades().contains(nome) ){
            throw new InvalidCommunityException("Usuario já faz parte dessa comunidade.");
        }

        if(!comunidades.containsKey(nome) || nova== null){
            throw new InvalidCommunityException("Comunidade não existe.");
        }

        nova.getMembros().add(newUser.getLogin());
        newUser.getComunidades().add(nome);


    }

    /**
     * Retorna as comunidades que o usuário participa.
     * @param login Login do usuário.
     * @return String com nomes das comunidades.
     * @throws InvalidUserException se o usuário não estiver cadastrado.
     */
    public String getComunidades(String login){

        User newUser = users.get(login);
        if (newUser == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        return "{" + String.join(",", newUser.getComunidades()) + "}";

    }

    /**
     * Envia uma mensagem para todos os membros de uma comunidade.
     * @param id ID do remetente.
     * @param nome Nome da comunidade.
     * @param mensagem Conteúdo da mensagem.
     * @throws InvalidUserException se o remetente não estiver cadastrado.
     * @throws InvalidCommunityException se a comunidade não existir.
     * @throws InvalidRecadoException se a mensagem for vazia.
     */
    public void enviarMensagem(String id, String nome, String mensagem){

        User newUser= getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        Comunidade nova=comunidades.get(nome);

        if(nova == null){
            throw new InvalidCommunityException("Comunidade não existe.");
        }

        if(mensagem.isEmpty()){
            throw new InvalidRecadoException("Mensagem vazia.");
        }

        for (String login : nova.getMembros()) {
            User membro = users.get(login);
            if (membro != null) {
                membro.getMensagensComunidades().add(mensagem);
            }
        }


    }

    /**
     * Lê e remove a primeira mensagem da fila de mensagens da comunidade do usuário.
     * @param id ID do usuário.
     * @return Mensagem lida.
     * @throws InvalidUserException se o usuário não estiver cadastrado.
     * @throws IllegalArgumentException se não houver mensagens.
     */
    public String lerMensagem(String id) {


        User newUser=getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if (newUser.getMensagensComunidades().isEmpty()) {
            throw new InvalidRecadoException("Não há mensagens.");
        }

        String recadoRemovido = newUser.getMensagensComunidades().remove(0);


        return recadoRemovido;

    }

    /**
     * Adiciona um ídolo para o usuário.
     * @param id ID do fã.
     * @param loginIdolo Login do ídolo.
     * @throws InvalidUserException se algum dos usuários não estiver cadastrado, se o usuário for o próprio ídolo, já for fã ou se forem inimigos.
     */
    public void adicionarIdolo(String id, String loginIdolo){

        User newUser= getUserById(id);
        User idoloUser= users.get(loginIdolo);

        if(newUser == null || idoloUser == null){
            throw new InvalidUserException("Usuário não cadastrado.");
        }


        if(idoloUser.getFas().contains(newUser.getLogin())){
            throw new InvalidUserException("Usuário já está adicionado como ídolo.");
        }

        if(newUser == idoloUser){
            throw new InvalidUserException("Usuário não pode ser fã de si mesmo.");
        }

        if(idoloUser.getInimigos().contains(newUser.getLogin())){
            throw new InvalidUserException("Função inválida: " + idoloUser.getNome() + " é seu inimigo.");
        }

        newUser.getIdolos().add(idoloUser.getLogin());
        idoloUser.getFas().add(newUser.getLogin());

    }

    /**
     * Retorna os fãs do usuário.
     * @param login Login do usuário.
     * @return Lista de fãs do usuário.
     */
    public String getFas(String login){

        User user=users.get(login);

        if(user!=null || user.getFas()!=null){

            List<String> fas = new ArrayList<>(user.getFas());

            return fas.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    /**
     * Verifica se um usuário é fã de outro.
     * @param login Login do possível fã.
     * @param idolo Login do possível ídolo.
     * @return true se for fã, false caso contrário.
     * @throws InvalidUserException se algum dos usuários não estiver cadastrado.
     */
    public boolean ehFa(String login, String idolo){

        User user=users.get(login);
        User idoloUser=users.get(idolo);


        if(user==null || idoloUser==null){
            throw new InvalidUserException("Usuário não cadastrado.");

        }

        if(user.getIdolos().contains(idoloUser.getLogin()) && idoloUser.getFas().contains(user.getLogin())){
            return true;
        }

        return false;
    }

    /**
     * Adiciona um usuário como paquera.
     * @param id ID do usuário.
     * @param paquera Login da paquera.
     * @throws InvalidUserException se algum dos usuários não estiver cadastrado, se já forem paqueras, se for a si mesmo ou se forem inimigos.
     */
    public void adicionarPaquera(String id, String paquera){

        User user= getUserById(id);
        User userPaquera= users.get(paquera);

        if(user== null || userPaquera==null){
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if (user.getPaqueras().contains(paquera)) {

            throw new InvalidUserException("Usuário já está adicionado como paquera.");
        }

        if(user== userPaquera){
            throw new InvalidUserException("Usuário não pode ser paquera de si mesmo.");
        }

        if(userPaquera.getInimigos().contains(user.getLogin())){
            throw new InvalidUserException("Função inválida: " + userPaquera.getNome() + " é seu inimigo.");
        }

        user.getPaqueras().add(userPaquera.getLogin());

        if(userPaquera.getPaqueras().contains(user.getLogin())){
            String msgParaUser = userPaquera.getNome() + " é seu paquera - Recado do Jackut.";
            String msgParaUserPaquera = user.getNome() + " é seu paquera - Recado do Jackut.";

            user.getRecados().add(new Recado(msgParaUser, userPaquera.getLogin()));
            userPaquera.getRecados().add(new Recado(msgParaUserPaquera, user.getLogin()));
        }

    }

    /**
     * Verifica se um usuário é paquera de outro.
     * @param id ID do usuário.
     * @param paquera Login do usuário para adicionar como paquera.
     * @return true se for paquera, false caso contrário.
     * @throws InvalidUserException se algum dos usuários não estiver cadastrado.
     */
    public boolean ehPaquera(String id, String paquera){

        User user=getUserById(id);
        User paqueraUser=users.get(paquera);


        if(user==null || paqueraUser==null){
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if(user.getPaqueras().contains(paqueraUser.getLogin())){
            return true;
        }

        return false;
    }

    /**
     * Retorna os paqueras de um usuário.
     * @param id ID do usuário.
     * @return Lista de paqueras.
     */
    public String getPaqueras(String id){

        User user=getUserById(id);

        if(user!=null || user.getPaqueras()!=null){

            List<String> fas = new ArrayList<>(user.getPaqueras());

            return fas.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    /**
     * Adiciona um usuário como inimigo.
     * @param id ID do usuário.
     * @param inimigo Login do inimigo.
     * @throws InvalidUserException se algum dos usuários não estiver cadastrado, se já forem inimigos ou se for a si mesmo.
     */
    public void adicionarInimigo(String id, String inimigo){

        User user= getUserById(id);
        User inimigoUser= users.get(inimigo);


        if(user==null ||inimigoUser==null){
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        if(user== inimigoUser){
            throw new IllegalArgumentException("Usuário não pode ser inimigo de si mesmo.");
        }

        if(user.getInimigos().contains(inimigo)){
            throw new IllegalArgumentException("Usuário já está adicionado como inimigo.");
        }

        user.getInimigos().add(inimigo);


    }

    /**
     * Remove um usuário do sistema, excluindo suas comunidades e relações.
     * @param id ID do usuário a ser removido.
     * @throws InvalidUserException se o usuário não estiver cadastrado.
     */
    public void removerUsuario(String id) {
        User user = getUserById(id);

        if (user == null) {
            throw new InvalidUserException("Usuário não cadastrado.");
        }

        String login = user.getLogin();
        ArrayList<String> comunidadesRemovidas = new ArrayList<>();


        for (Iterator<Comunidade> it = comunidades.values().iterator(); it.hasNext(); ) {
            Comunidade c = it.next();
            if (c.getLoginCriador().equals(login)) {
                comunidadesRemovidas.add(c.getNomeComunidade());
                it.remove();
            }
        }


        for (User u : users.values()) {
            u.getComunidades().removeAll(comunidadesRemovidas);

            u.getAmigos().remove(login);
            u.getSolicitacoesAmizade().remove(login);
            u.getFas().remove(login);
            u.getIdolos().remove(login);
            u.getPaqueras().remove(login);
            u.getInimigos().remove(login);
            u.getRecados().removeIf(recado -> recado.getLoginRemetente().equals(login));
        }

        users.remove(login);
        encerrarSistema();
    }

}