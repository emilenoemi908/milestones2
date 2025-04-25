import java.io.*;
import java.util.*;

/**
 * Repositorio: Gerencia(abre sess�es, cria usu�rios,carrega e salva dados) usu�rios, suas sess�es, amizades e envia/ler mensagens/recados.
 */
public class Repositorio {

    private Map<String, User> users= new HashMap<>();
    private Map<String, Comunidade> comunidades = new HashMap<>();


    /**
     * Construtor do reposit�rio.
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
            System.out.println("Erro ao carregar usu�rios: " + e.getMessage());
        }

    }


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
     * Reseta o repositorio de usu�rios, removendo todos e deletando o arquivo de dados "users.dat".
     */

    public void zerarSistema() {

        users.clear();

        File arquivo = new File("users.dat");

        if (arquivo.exists()) {
            arquivo.delete();
        } else {
            System.out.println("O arquivo n�o existe.");
        }

        comunidades.clear();
        File arquivoComunidad = new File("comunidades.dat");
        if (arquivoComunidad.exists()) {
            arquivoComunidad.delete();
        } else {
            System.out.println("O arquivo de comunidades n�o existe.");
        }
    }

    /**
     * Salva os dados dos usu�rios no arquivo "users.dat".
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
     * Finaliza a execu��o do repositorio.
     */
    public void quit(){

        System.exit(0);
    }

    /**
     * Busca o usu�rio pelo Id da sess�o.
     * @param id ID sess�o do usu�rio.
     * @return User correspondente ao id ou null se n�o for encontrado.
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
     * Cria um novo usu�rio.
     * @param login Nome do login do usu�rio.
     * @param senha Senha do usu�rio.
     * @param nome Nome do usu�rio.
     * @throws IllegalArgumentException Se os dados forem inv�lidos.
     */
    public void CriarUsuario(String login, String senha, String nome){

        if (login == null || login.trim().isEmpty() ) {
            throw new IllegalArgumentException("Login inv�lido.");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Senha inv�lida.");
        }


        if(users.containsKey(login)){
            throw new IllegalArgumentException("Conta com esse nome j� existe.");
        }

        User newUser= new User (login, senha, nome);
        users.put(login, newUser);

    }

    /**
     * Retorna um atributo espec�fico do usu�rio.
     * @param login Nome do login do usu�rio.
     * @param atributo Nome do atributo que deve ser recuperado.
     * @return Valor do atributo solicitado.
     * @throws InvalidUserException Se o usu�rio n�o existir.
     * @throws InvalidAtributeException Se o atributo n�o estiver preenchido.
     */
    public String getAtributoUsuario(String login, String atributo) {

        User user = users.get(login);

        if (user==null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
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
                    throw new InvalidAtributeException("Atributo n�o preenchido.");
                }


                String nome = atributoExtra.getNome();
                String valor = atributoExtra.getValor();

                if (valor != null && !valor.trim().isEmpty()) {
                    return valor;
                }
                else {
                    throw new IllegalArgumentException("Atributo n�o preenchido.");
                }

        }
    }

    /**
     * Abre uma sess�o para um usu�rio.
     * @param login Nome do login do usu�rio.
     * @param senha Senha do usu�rio.
     * @return ID da sess�o gerado.
     * @throws IllegalArgumentException Se o login ou senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {



        if (login == null || senha == null || login.trim().isEmpty() || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Login ou senha inv�lidos.");
        }

        User user = users.get(login);

        if (user == null || !senha.equals(user.getSenha())) {
            throw new IllegalArgumentException("Login ou senha inv�lidos.");
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
     * Edita o perfil do usu�rio, alterando um atributo especifico (login, nome, senha) ou preenchendo um novo.
     * @param id ID sess�o do usu�rio.
     * @param atributo Atributo a ser editado.
     * @param valor Novo valor do atributo.
     * @throws InvalidUserException Se o usu�rio n�o for encontrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {

        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        User usuarioEncontrado = getUserById(id);


        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
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
     * Adiciona um amigo � lista de amigos do usu�rio.
     * @param id ID do usu�rio.
     * @param amigo Nome do amigo a ser adicionado.
     * @throws InvalidUserException Se o amigo n�o existir ou se j� forem amigos.
     */
    public  void adicionarAmigo(String id, String amigo) {

        User userAmigo = users.get(amigo);

        if (userAmigo == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if (id == null || id.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (amigo == null || amigo.trim().isEmpty()) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        User usuario = getUserById(id);


        if (usuario == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (!users.containsKey(amigo)) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if (usuario.getIdSessao().equals(userAmigo.getIdSessao())) {
            throw new InvalidUserException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        }

        if (usuario.getAmigos().contains(amigo)) {
            throw new InvalidUserException("Usu�rio j� est� adicionado como amigo.");
        }

        if(userAmigo.getInimigos().contains(usuario.getLogin())){
            throw new IllegalArgumentException("Fun��o inv�lida: " + userAmigo.getNome() + " � seu inimigo.");
        }

        if (userAmigo.getSolicitacoesAmizade().contains(usuario.getLogin()) || usuario.getSolicitacoesAmizade().contains(userAmigo)) {


            usuario.getAmigos().add(userAmigo.getLogin());
            userAmigo.getAmigos().add(usuario.getLogin());


            usuario.getSolicitacoesAmizade().remove(amigo);
            userAmigo.getSolicitacoesAmizade().remove(usuario.getLogin());
        }


        if (usuario.getSolicitacoesAmizade().contains(amigo) || userAmigo.getSolicitacoesAmizade().contains(usuario.getNome())) {

            throw new IllegalArgumentException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        }

        usuario.getSolicitacoesAmizade().add(userAmigo.getLogin());


    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     * @param login Nome de login de um dos usu�rios.
     * @param amigo Nome de um dos usu�rios.
     * @return true se forem amigos, false caso contr�rio.
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
     * Exibi a lista de amigos do usu�rio.
     * @param login Nome de login do usu�rio.
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
     * Envia recado/mensagem para outro usu�rio.
     * @param id ID sess�o do usu�rio.
     * @param destinatario Nome do destinat�rio da mensagem.
     * @param mensagem Conte�do do recado/mensagem.
     * @throws InvalidRecadoException Se houver erro no envio do recado.
     */
    public void enviarRecado (String id, String destinatario, String mensagem) throws InvalidRecadoException {

        if (id == null || id.trim().isEmpty() || destinatario == null || destinatario.trim().isEmpty()) {
            throw new InvalidRecadoException("ID ou destinat�rio inv�lido.");
        }

        User userDestinatario = users.get(destinatario);
        User user = getUserById(id);

        if (user == null || userDestinatario == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (id.equals(userDestinatario.getIdSessao())) {
            throw new InvalidUserException("Usu�rio n�o pode enviar recado para si mesmo.");
        }

        if (mensagem == null || mensagem.trim().isEmpty()) {
            throw new InvalidRecadoException("Mensagem inv�lida.");
        }
        if(userDestinatario.getInimigos().contains(user.getLogin())){
            throw new IllegalArgumentException("Fun��o inv�lida: " + userDestinatario.getNome() + " � seu inimigo.");
        }


        Recado recado = new Recado(mensagem, user.getLogin());
        userDestinatario.getRecados().add(recado);
    }

    /**
     * Faz a leitura do recado mais antigo da lista de racdos do usu�rio.
     * @param id ID sess�o do usu�rio.
     * @return Recado removido da lista de recados.
     * @throws InvalidRecadoException Se n�o houver recados.
     * @throws InvalidUserException Se o usu�rio n�o existir.
     * @throws IllegalArgumentException Se id for inv�lido.
     */
    public String lerRecado (String id) throws InvalidRecadoException {

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID inv�lido.");
        }

        User usuarioEncontrado = getUserById(id);

        if (usuarioEncontrado == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if (usuarioEncontrado.getRecados().isEmpty()) {
            throw new InvalidRecadoException("N�o h� recados.");
        }

        Recado recadoRemovido = usuarioEncontrado.getRecados().remove(0);

        return recadoRemovido.getMensagem();

    }

    public void criarComunidade(String sessao, String nome, String descricao){

        User newUser=getUserById(sessao);

        if (newUser == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        if(comunidades.containsKey(nome)){
            throw new IllegalArgumentException("Comunidade com esse nome j� existe.");
        }

        Comunidade novaComunidade= new Comunidade(nome, newUser.getLogin(), descricao);
        comunidades.put(nome, novaComunidade);
        novaComunidade.getMembros().add(newUser.getLogin());

        newUser.getComunidades().add(novaComunidade.getNomeComunidade());

    }

    public String getDescricaoComunidade(String nome) {


        Comunidade nova = comunidades.get(nome);


        if (nova == null || !comunidades.containsKey(nova.getNomeComunidade())) {
            throw new IllegalArgumentException("Comunidade n�o existe.");
        }

        return nova.getDescricao();

    }

    public String getDonoComunidade(String nome) {

        Comunidade nova = comunidades.get(nome);
        if (nova == null) {
            throw new IllegalArgumentException("Comunidade n�o existe.");
        }

        return nova.getLoginCriador();

    }

    public String getMembrosComunidade(String nome){

        Comunidade nova = comunidades.get(nome);

        if (nova == null) {
            throw new IllegalArgumentException("Comunidade n�o existe.");
        }

        return "{" + String.join(",", nova.getMembros()) + "}";


    }

    public void adicionarComunidade(String id, String nome){

        User newUser= getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        Comunidade nova= comunidades.get(nome);

        if(newUser.getComunidades().contains(nome) ){
            throw new IllegalArgumentException("Usuario j� faz parte dessa comunidade.");
        }

        if(!comunidades.containsKey(nome) || nova== null){
            throw new IllegalArgumentException("Comunidade n�o existe.");
        }

        nova.getMembros().add(newUser.getLogin());
        newUser.getComunidades().add(nome);


    }

    public String getComunidades(String login){


        User newUser = users.get(login);
        if (newUser == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        return "{" + String.join(",", newUser.getComunidades()) + "}";


    }

    public void enviarMensagem(String id, String nome, String mensagem){

        User newUser= getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }

        Comunidade nova=comunidades.get(nome);

        if(nova == null){
            throw new IllegalArgumentException("Comunidade n�o existe.");
        }

        if(mensagem.isEmpty()){
            throw new IllegalArgumentException("Mensagem vazia.");
        }

        for (String login : nova.getMembros()) {
            User membro = users.get(login);
            if (membro != null) {
                membro.getMensagensComunidades().add(mensagem);
            }
        }


    }

    public String lerMensagem(String id) {


        User newUser=getUserById(id);

        if (newUser == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if (newUser.getMensagensComunidades().isEmpty()) {
            throw new IllegalArgumentException("N�o h� mensagens.");
        }

        String recadoRemovido = newUser.getMensagensComunidades().remove(0);


        return recadoRemovido;

    }

    public void adicionarIdolo(String id, String idolo){

        User newUser= getUserById(id);
        User idoloUser= users.get(idolo);

        if(newUser == null || idoloUser == null){
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
        }


        if(idoloUser.getFas().contains(newUser.getLogin())){
            throw new IllegalArgumentException("Usu�rio j� est� adicionado como �dolo.");
        }

        if(newUser == idoloUser){
            throw new IllegalArgumentException("Usu�rio n�o pode ser f� de si mesmo.");
        }

        if(idoloUser.getInimigos().contains(newUser.getLogin())){
            throw new IllegalArgumentException("Fun��o inv�lida: " + idoloUser.getNome() + " � seu inimigo.");
        }

        newUser.getIdolos().add(idoloUser.getLogin());
        idoloUser.getFas().add(newUser.getLogin());

    }

    public String getFas(String login){

        User user=users.get(login);

        if(user!=null || user.getFas()!=null){

            List<String> fas = new ArrayList<>(user.getFas());

            return fas.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    public boolean ehFa(String login, String idolo){

        User user=users.get(login);
        User idoloUser=users.get(idolo);


        if(user==null || idoloUser==null){
            throw new InvalidUserException("Usu�rio n�o cadastrado.");

        }

        if(user.getIdolos().contains(idoloUser.getLogin()) && idoloUser.getFas().contains(user.getLogin())){
            return true;
        }

        return false;
    }

    public void adicionarPaquera(String id, String paquera){

        User user= getUserById(id);
        User userPaquera= users.get(paquera);

        if(user== null || userPaquera==null){
            throw new IllegalArgumentException("Usu�rio n�o cadastrado.");
        }

        if (user.getPaqueras().contains(paquera)) {

            throw new IllegalArgumentException("Usu�rio j� est� adicionado como paquera.");
        }

        if(user== userPaquera){
            throw new IllegalArgumentException("Usu�rio n�o pode ser paquera de si mesmo.");
        }

        if(userPaquera.getInimigos().contains(user.getLogin())){
            throw new IllegalArgumentException("Fun��o inv�lida: " + userPaquera.getNome() + " � seu inimigo.");
        }

        user.getPaqueras().add(userPaquera.getLogin());

        if(userPaquera.getPaqueras().contains(user.getLogin())){
            String msgParaUser = userPaquera.getNome() + " � seu paquera - Recado do Jackut.";
            String msgParaUserPaquera = user.getNome() + " � seu paquera - Recado do Jackut.";

            user.getRecados().add(new Recado(msgParaUser, userPaquera.getLogin()));
            userPaquera.getRecados().add(new Recado(msgParaUserPaquera, user.getLogin()));
        }

    }

    public boolean ehPaquera(String id, String paquera){

        User user=getUserById(id);
        User paqueraUser=users.get(paquera);


        if(user==null || paqueraUser==null){
            throw new InvalidUserException("Usu�rio n�o cadastrado.");

        }

        if(user.getPaqueras().contains(paqueraUser.getLogin())){
            return true;
        }

        return false;
    }

    public String getPaqueras(String id){

        User user=getUserById(id);

        if(user!=null || user.getPaqueras()!=null){

            List<String> fas = new ArrayList<>(user.getPaqueras());

            return fas.toString().replace("[", "{").replace("]", "}").replace(", ", ",");

        }

        return "{}";

    }

    public void adicionarInimigo(String id, String inimigo){

        User user= getUserById(id);
        User inimigoUser= users.get(inimigo);


        if(user==null ||inimigoUser==null){
            throw new InvalidUserException("Usu�rio n�o cadastrado.");

        }

        if(user== inimigoUser){
            throw new IllegalArgumentException("Usu�rio n�o pode ser inimigo de si mesmo.");
        }

        if(user.getInimigos().contains(inimigo)){
            throw new IllegalArgumentException("Usu�rio j� est� adicionado como inimigo.");
        }

        user.getInimigos().add(inimigo);


    }

    public void removerUsuario(String id) {
        User user = getUserById(id);

        if (user == null) {
            throw new InvalidUserException("Usu�rio n�o cadastrado.");
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